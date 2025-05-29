package client;

import exception.ResponseException;

import java.util.Arrays;

public class PreloginClient {

    public String eval(String input) throws ResponseException {
        var tokens = input.toLowerCase().split(" ");
        var cmd = (tokens.length > 0) ? tokens[0] : "help";
        var params = Arrays.copyOfRange(tokens, 1, tokens.length);
        return switch (cmd) {
            case "login", "l" -> login(params);
            case "register", "r" -> register(params);
            case "quit" -> "quit";
            default -> help();
        };
    }

    public String login(String... params) throws ResponseException {
        if (params.length >= 2) {
            return "You are now signed in as " + params[0];
        }
        throw new ResponseException(400, "Expected: <USERNAME> <PASSWORD>");
    }

    public String register(String... params) throws ResponseException {
        if (params.length >= 3) {
            return "Successfully registered. You are now signed in as " + params[0];
        }
        throw new ResponseException(400, "Expected: <USERNAME> <PASSWORD>");
    }

    public String help() {
        return """
                Options:
                - Login as an existing user: "l", "login" <USERNAME> <PASSWORD>
                - Register a new user: "r", "register" <USERNAME> <PASSWORD> <EMAIL>
                - Exit the program: "q", "quit"
                - Print this message: "h", "help"
                """;
    }
}
