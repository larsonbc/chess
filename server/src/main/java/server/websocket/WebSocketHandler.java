package server.websocket;

import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import service.GameService;
import service.UserService;
import websocket.commands.UserGameCommand;
import websocket.messages.ServerMessage;

import java.io.IOException;

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
    public void onMessage(Session session, String msg) {
        try {
            UserGameCommand command = new Gson().fromJson(msg, UserGameCommand.class);

            // Throws a custom Unauthorized Exception - mine may work differently
            //String username = userService.getUsernameFromAuth(command.getAuthToken());
            String username = getUsername(command.getAuthToken());

            saveSession(command.getGameID(), session);

            switch (command.getCommandType()) {
                case CONNECT -> connect(session, username, (UserGameCommand) command); //change from UserGameCommand to Connect Command
//                case MAKE_MOVE -> makeMove(session, username, (MakeMoveCommand) command);
//                case LEAVE -> leaveGame(session, username, (LeaveGameCommand) command);
//                case RESIGN -> resign(session, username, (ResignCommand) command);
                default -> System.out.println("Not able to do stuff");

            }
//        } catch (UnauthorizedException ex) {
//            // Serializes and sends the error message
//            sendMessage(session.getRemote(), new ErrorMessage("Error: unauthorized"));
//        }
        } catch (Exception ex) {
            ex.printStackTrace();
            //sendMessage(session.getRemote(), new ErrorMessage("Error: " + ex.getMessage()));
        }
    }

    private void connect(Session session, String username, UserGameCommand command) throws IOException {
        connections.add(username, session);
        int numGames = gameService.getGames().size();
        if (command.getGameID() < 0 || command.getGameID() > numGames) {
            connections.sendError(username, "Error: Invalid Game ID");
            return;
        }
        ChessGame game = new ChessGame(); // may need to change to get game from server with command game ID
        connections.sendLoadGame(username, game);
        var message = String.format("%s has joined the game", username);
        connections.broadcast(username, message);
    }

    private String getUsername(String authToken) {
        try {
            return userService.getUsernameFromAuth(authToken);
        } catch (DataAccessException e) {
            return "null";
        }
    }

    private void saveSession(int gameID, Session session) {
        //make sure session is in connection manager
    }
}
