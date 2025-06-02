package client;

import exception.ResponseException;
import request.LoginRequest;
import request.RegisterRequest;
import server.ServerFacade;

import java.util.Arrays;

public class PreloginClient {
    private final StateHandler stateHandler;
    private final ServerFacade facade;

    public PreloginClient(StateHandler stateHandler, ServerFacade facade) {
        this.stateHandler = stateHandler;
        this.facade = facade;
    }

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
        if (params.length == 2) {
            var username = params[0];
            var password = params[1];
            var request = new LoginRequest(username, password);
            try {
                var result = facade.login(request);
                stateHandler.setState(State.SIGNEDIN);
                stateHandler.setAuthToken(result.authToken());
                return "You are now signed in as " + username + ".";
            } catch (ResponseException e) {
                String message = e.getMessage().toLowerCase();
                if (message.contains("unauthorized")) {
                    throw new ResponseException(401, "Invalid username or password.");
                } else {
                    throw e; // rethrow other unexpected errors
                }
            }
        }
        throw new ResponseException(400, "Expected: <USERNAME> <PASSWORD>");
    }

    public String register(String... params) throws ResponseException {
        if (params.length == 3) {
            var request = new RegisterRequest(params[0], params[1], params[2]);
            try {
                var result = facade.register(request);
                stateHandler.setState(State.SIGNEDIN);
                stateHandler.setAuthToken(result.authToken());
                return "Successfully registered. You are now signed in as " + params[0] + ".";
            } catch (ResponseException e) {
                String message = e.getMessage().toLowerCase();
                if (message.contains("already taken")) {
                    throw new ResponseException(400, "Username is already taken.");
                } else {
                    throw e; // Rethrow other errors unchanged
                }
            }
        }
        throw new ResponseException(400, "Expected: <USERNAME> <PASSWORD> <EMAIL>");
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
