package client;

import exception.ResponseException;

import java.util.Arrays;

public class PostloginClient {

    private final StateHandler stateHandler;

    public PostloginClient(StateHandler stateHandler) {
        this.stateHandler = stateHandler;
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
        return "List of Games will go here";
    }

    public String createGame(String... params) throws ResponseException {
        if (params.length == 1) {
            return "Game created. Game name: " + params[0];
        } else {
            throw new ResponseException(400, "Expected: <GAME NAME>");
        }
    }

    public String joinGame(String... params) throws ResponseException {
        if (params.length == 2) {
            return "Joined game with ID: " + params[0] + ", joined as " + params[1];
        } else {
            return "Expected: <GAME ID> <COLOR>";
        }
    }

    public String watchGame(String... params) throws ResponseException {
        if (params.length == 1) {
            return "Watching game with ID: " + params[0];
        } else {
            return "Expected: <GAME ID>";
        }
    }

    public String logout() {
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
