package client;

import chess.ChessGame;
import exception.ResponseException;
import model.GameData;
import request.CreateGameRequest;
import request.JoinGameRequest;
import request.LogoutRequest;
import server.ServerFacade;
import ui.ChessBoardPrinter;

import java.util.Arrays;

public class PostloginClient {

    private final StateHandler stateHandler;
    private final ServerFacade facade;

    public PostloginClient(StateHandler stateHandler, ServerFacade facade) {
        this.stateHandler = stateHandler;
        this.facade = facade;
    }

    public String eval(String input) throws ResponseException {
        var tokens = input.toLowerCase().split(" ");
        var cmd = (tokens.length > 0) ? tokens[0] : "help";
        var params = Arrays.copyOfRange(tokens, 1, tokens.length);
        return switch (cmd) {
            case "list", "l" -> listGames();
            case "create", "c" -> createGame(params);
            case "join", "j" -> joinGame(params);
            case "watch", "w" -> watchGame(params);
            case "logout" -> logout();
            default -> help();
        };
    }

    public String listGames() throws ResponseException {
        var games = facade.listGames(stateHandler.getAuthToken());
        var result = new StringBuilder();
        if (games.games().isEmpty()) {
            return "No current games";
        }
        int index = 1;
        for (var game : games.games()) {
            result.append(String.format(
                    "%d. Game Name: %s    White: %s    Black: %s%n",
                    index++,
                    game.gameName(),
                    game.whiteUsername() != null ? game.whiteUsername() : "<empty>",
                    game.blackUsername() != null ? game.blackUsername() : "<empty>"
            ));
        }
        return result.toString();
    }

    public String createGame(String... params) throws ResponseException {
        if (params.length == 1) {
            var gameName = params[0];
            var request = new CreateGameRequest(gameName);
            facade.createGame(request, stateHandler.getAuthToken());
            return "Successfully created game " + params[0];
        } else {
            throw new ResponseException(400, "Expected: <GAME NAME>");
        }
    }

    public String joinGame(String... params) throws ResponseException {
        if (params.length == 2) {
            int gameID;
            try {
                gameID = Integer.parseInt(params[0]);
            } catch (NumberFormatException e) {
                throw new ResponseException(400, "Game ID must be a number");
            }
            var color = params[1].toUpperCase();
            var request = new JoinGameRequest(color, gameID);
            try {
                facade.joinGame(request, stateHandler.getAuthToken());
            } catch (ResponseException e) {
                if (e.getMessage().toLowerCase().contains("bad request")) {
                    throw new ResponseException(400, "Could not join game: Invalid game ID or color");
                } else {
                    throw e; // rethrow if it's something else
                }
            }
            GameData gameData = facade.getGame(stateHandler.getAuthToken(), gameID);
            ChessGame game = gameData.game();
            String gameName = gameData.gameName();
            System.out.print("\u001b[0m");
            ChessBoardPrinter.printBoard(game.getBoard(), color.equals("WHITE"));
            stateHandler.setState(State.GAMEPLAY);
            return "Joined game: " + gameName + ", joined as " + params[1];
        } else {
            throw new ResponseException(400, "Expected: <GAME ID> <COLOR>");
        }
    }

    public String watchGame(String... params) throws ResponseException {
        if (params.length == 1) {
            int id;
            try {
                id = Integer.parseInt(params[0]);
            } catch (NumberFormatException e) {
                throw new ResponseException(400, "Game ID must be a number");
            }
            int gameID = Integer.parseInt(params[0]);
            int numGames = facade.listGames(stateHandler.getAuthToken()).games().size();
            if (gameID < 1 || gameID > numGames) {
                throw new ResponseException(400, "Invalid game ID.");
            }
            GameData gameData = facade.getGame(stateHandler.getAuthToken(), gameID);
            ChessGame game = gameData.game();
            String gameName = gameData.gameName();
            System.out.print("\u001b[0m"); // Reset any previous color
            ChessBoardPrinter.printBoard(game.getBoard(), true);
            return "Watching game: " + gameName;
        } else {
            return "Expected: <GAME ID>";
        }
    }

    public String logout() throws ResponseException {
        var authToken = stateHandler.getAuthToken();
        var request = new LogoutRequest(authToken);
        facade.logout(request);

        stateHandler.setAuthToken(null);
        stateHandler.setState(State.SIGNEDOUT);
        return "You are now logged out";
    }

    public String help() {
        return """
                Options:
                - List current games: "l", "list"
                - Create a new game: "c", "create" <GAME NAME>
                - Join a game: "j", "join" <GAME ID> <COLOR>
                - Watch a game: "w", "watch" <GAME ID>
                - Logout: "logout"
                """;
    }

}
