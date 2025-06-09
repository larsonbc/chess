package client;

import client.websocket.ServerMessageObserver;
import model.GameData;
import client.websocket.WebSocketFacade;

import java.util.Arrays;

public class GameplayClient {
    private final StateHandler stateHandler;
    private GameData gameData;
    //private final WebSocketFacade ws;

    public GameplayClient(StateHandler stateHandler, GameData gameData) {
        this.stateHandler = stateHandler;
        this.gameData = gameData;
    }

    public String eval(String input) {
        var tokens = input.toLowerCase().split(" ");
        var cmd = (tokens.length > 0) ? tokens[0] : "help";
        var params = Arrays.copyOfRange(tokens, 1, tokens.length);
        return switch (cmd) {
            case "highlight", "hi" -> highlightLegalMoves();
            case "m", "move", "make" -> makeMove();
            case "r", "redraw" -> redrawBoard();
            case "res", "resign" -> resign();
            case "leave" -> leave();
            default -> help();
        };
    }

    public String highlightLegalMoves() {
        return "highlight";
    }

    public String makeMove() {
        return "move";
    }

    public String redrawBoard() {
        return "redraw";
    }

    public String resign() {
        return "resign";
    }

    public String leave() {
        stateHandler.setState(State.SIGNEDIN);
        return "leave";
    }

    public String help() {
        return """
                Options:
                - Highlight legal movies: "hi", "highlight" <position> (e.g. f5)
                - Make a move: "m", "move" "make" <source> <destination> <optional promotion> (e.g. f5 e4 q)
                - Redraw Chess Board: "r", "redraw"
                - Resign from game: "res", "resign"
                - Leave game: "leave"
                """;
    }
}
