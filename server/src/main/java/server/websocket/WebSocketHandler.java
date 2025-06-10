package server.websocket;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPiece;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dataaccess.DataAccessException;
import exception.UnauthorizedException;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import service.GameService;
import service.UserService;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

@WebSocket
public class WebSocketHandler {

    private final ConnectionManager connections = new ConnectionManager();
    private final UserService userService;
    private final GameService gameService;

    public WebSocketHandler(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String msg) throws IOException {
        try {
            JsonObject json = JsonParser.parseString(msg).getAsJsonObject();
            String commandType = json.get("commandType").getAsString();
            String authToken = json.get("authToken").getAsString();
            String username = getUsername(authToken, session);

            switch (commandType) {
                case "CONNECT" -> {
                    //connect(session, username, (UserGameCommand) command); //change from UserGameCommand to Connect Command
                    UserGameCommand command = new Gson().fromJson(msg, UserGameCommand.class);
                    connect(session, username, command);
                }
                case "MAKE_MOVE" -> {
                    MakeMoveCommand command = new Gson().fromJson(msg, MakeMoveCommand.class);
                    makeMove(username, command);
                }
                case "LEAVE" -> {
                    UserGameCommand command = new Gson().fromJson(msg, UserGameCommand.class);
                    leave(session, username, command);
                }
                case "RESIGN" -> {
                    UserGameCommand command = new Gson().fromJson(msg, UserGameCommand.class);
                    resign(session, username, command);
                }
                default -> System.out.println("Not able to do stuff");

            }
        } catch (UnauthorizedException ex) {
            ErrorMessage errorMessage = new ErrorMessage(ServerMessage.ServerMessageType.ERROR);
            errorMessage.setErrorMessage("Error: Invalid Auth Token");
            session.getRemote().sendString(new Gson().toJson(errorMessage));
        }
    }

    private void leave(Session session, String username, UserGameCommand command) throws IOException {
        int gameID = command.getGameID();
        GameData gameData = gameService.getGames().get(command.getGameID() - 1);
        if (gameData == null) {
            connections.sendError(username, "Error: Invalid Game ID");
        }
        ChessGame game = gameData.game();
        boolean isPlayer = false;
        GameData updatedGameData = gameData;
        // Check if the user is white or black player and not observer
        if (username.equals(gameData.whiteUsername())) {
            updatedGameData = new GameData(gameID, null, gameData.blackUsername(), gameData.gameName(), game);
            isPlayer = true;
        } else if (username.equals(gameData.blackUsername())) {
            updatedGameData = new GameData(gameID, gameData.whiteUsername(), null, gameData.gameName(), game);
            isPlayer = true;
        }
        // Save updated game if player left
        if (isPlayer) {
            try {
                gameService.updateGamePlayers(updatedGameData); // You'll need this method (see below)
                gameService.saveGame(gameID, game); // Always save the game state
            } catch (DataAccessException e) {
                connections.sendError(username, "Error: Failed to update game.");
                return;
            }
        }
        // Remove the user from WebSocket connections
        connections.remove(username);
        var message = String.format("%s has left the game", username);
        connections.broadCastToGame(command.getGameID(), username, message);
    }

    private void resign(Session session, String username, UserGameCommand command) throws IOException {
        GameData gameData = gameService.getGames().get(command.getGameID() - 1);
        if (gameData == null) {
            connections.sendError(username, "Error: Invalid Game ID");
        }
        ChessGame game = gameData.game();
        ChessGame.TeamColor playerColor = null;
        // Doesn't allow observer to resign
        if (username.equals(gameData.whiteUsername())) {
            playerColor = ChessGame.TeamColor.WHITE;
        } else if (username.equals(gameData.blackUsername())) {
            playerColor = ChessGame.TeamColor.BLACK;
        } else {
            connections.sendError(username, "Error: Spectators cannot resign.");
            return;
        }
        // Doesn't allow multiple resignations
        if (game.isResigned()) {
            connections.sendError(username, "Error: Game already resigned.");
            return;
        }
        // Set resignation in game and update game state in database
        game.resign(playerColor);
        try {
            gameService.saveGame(command.getGameID(), game);
        } catch (DataAccessException e) {
            connections.sendError(username, "Error: Failed to save game state.");
            return;
        }
        int numGames = gameService.getGames().size();
        if (command.getGameID() < 0 || command.getGameID() > numGames) {
            connections.sendError(username, "Error: Invalid Game ID");
            return;
        }
        connections.sendResignNotification(username, command.getGameID());
    }

    private void makeMove(String username, MakeMoveCommand command) throws IOException {
        GameData gameData = gameService.getGames().get(command.getGameID() - 1);
        if (gameData == null) {
            connections.sendError(username, "Error: Game not found.");
            return;
        }
        ChessGame game = gameData.game();
        ChessMove move = command.getMove();
        // Determine player's team color
        ChessGame.TeamColor playerColor = null;
        if (username.equals(gameData.whiteUsername())) {
            playerColor = ChessGame.TeamColor.WHITE;
        } else if (username.equals(gameData.blackUsername())) {
            playerColor = ChessGame.TeamColor.BLACK;
        } else {
            connections.sendError(username, "Error: Spectators cannot make moves.");
            return;
        }
        // Check if any player has resigned
        if (game.isResigned()) {
            connections.sendError(username, "Error: The game is over due to resignation.");
            return;
        }
        // Check if the game is over (checkmate or stalemate)
        if (game.isInCheckmate(ChessGame.TeamColor.WHITE) || game.isInCheckmate(ChessGame.TeamColor.BLACK) ||
                game.isInStalemate(ChessGame.TeamColor.WHITE) || game.isInStalemate(ChessGame.TeamColor.BLACK)) {
            connections.sendError(username, "Error: The game is over.");
            return;
        }
        // Check if it's the player's turn
        if (playerColor != game.getTeamTurn()) {
            connections.sendError(username, "Error: It's not your turn.");
            return;
        }
        // Check that the piece belongs to the player
        ChessPiece movingPiece = game.getBoard().getPiece(move.getStartPosition());
        if (movingPiece == null || movingPiece.getTeamColor() != playerColor) {
            connections.sendError(username, "Error: You can only move your own pieces.");
            return;
        }
        // Validate move
        ArrayList<ChessMove> validMoves = (ArrayList<ChessMove>) game.validMoves(move.getStartPosition());
        if (validMoves == null || !validMoves.contains(move)) {
            connections.sendError(username, "Error: Invalid move.");
            return;
        }
        //Make move and update game in database
        try {
            game.makeMove(move);
            gameService.saveGame(command.getGameID(), game);
        } catch (InvalidMoveException | DataAccessException e) {
            connections.sendError(username, "Error: Invalid move.");
            return;
        }
        connections.sendLoadGame(username, game, command.getGameID(), move, true);
        var message = String.format("%s has made a move", username);
        connections.broadCastToGame(command.getGameID(), username, message);
    }

    private void connect(Session session, String username, UserGameCommand command) throws IOException {
        connections.add(username, session, command.getGameID());
        int numGames = gameService.getGames().size();
        if (command.getGameID() < 0 || command.getGameID() > numGames) {
            connections.sendError(username, "Error: Invalid Game ID");
            return;
        }
        ChessGame game = gameService.getGames().get(command.getGameID() - 1).game();
        connections.sendLoadGame(username, game, command.getGameID(), null, false);
        //var message = String.format("%s has joined the game", username);
        var message = "";
        if (command.getColor() == null) {
            message = String.format("%s has joined the game as observer", username);
        } else if (Objects.equals(command.getColor(), "WHITE")) {
            message = String.format("%s has joined the game as white", username);
        } else {
            message = String.format("%s has joined the game as black", username);
        }
        connections.broadCastToGame(command.getGameID(), username, message);
    }

    private String getUsername(String authToken, Session session) throws UnauthorizedException, IOException {
        try {
            return userService.getUsernameFromAuth(authToken);
        } catch (DataAccessException e) {
            throw new UnauthorizedException(401, "Error, Invalid Auth Token");
        }
    }

}
