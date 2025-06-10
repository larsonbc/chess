package client;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;
import model.GameData;
import ui.ChessBoardPrinter;

import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

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
            case "highlight", "hi" -> highlightLegalMoves(params);
            case "m", "move", "make" -> makeMove(params);
            case "r", "redraw" -> redrawBoard();
            case "res", "resign" -> resign();
            case "leave" -> leave();
            default -> help();
        };
    }

    public String highlightLegalMoves(String... params) {
        if (params.length != 1) {
            return "Expected: <position> (e.g. f5)";
        }
        String input = params[0];
        if (input.length() != 2) {
            return "Invalid Chess Position";
        }
        ChessGame game = gameData.game();
        ChessPosition position = convertToChessPosition(input);
        if (position == null) {
            return "Invalid Chess Position. Please format as follows:\n" +
                    "Letter (column) a-h followed by number 1-8 (row), e.g. f5";
        }
        Collection<ChessMove> legalMoves = game.validMoves(position);
        if (legalMoves == null || legalMoves.isEmpty()) {
            return "No legal moves for this piece";
        }
        Set<ChessPosition> destinations = legalMoves.stream()
                .map(ChessMove::getEndPosition)
                .collect(Collectors.toSet());
        System.out.print("\u001b[0m");
        ChessBoardPrinter.highlightMoves(game.getBoard(), stateHandler.getPlayerColor().equals("WHITE"), destinations, position);
        return "";
    }

    public String makeMove(String... params) {
        if (params.length < 2 || params.length > 3) {
            return "Expected: <source> <destination> <optional promotion> (e.g. f5 e4 q)";
        }
        for (int i = 0; i < 2; i++) {
            if (params[i].length() != 2) {
                return "Invalid Chess Position";
            }
        }
        //boolean promotion = false;
        ChessPiece.PieceType promotionPiece = null;
        if (params.length == 3) {
            if (!params[2].matches("(?i)[qbnr]")) {
                return "Invalid promotion piece. Promotion piece must be one of the following:\n" +
                        "q, r, b, n ";
            } else {
                //promotion = true;
                char pieceChar = Character.toLowerCase(params[2].charAt(0));
                switch (pieceChar) {
                    case 'q' -> promotionPiece = ChessPiece.PieceType.QUEEN;
                    case 'r' -> promotionPiece = ChessPiece.PieceType.ROOK;
                    case 'b' -> promotionPiece = ChessPiece.PieceType.BISHOP;
                    case 'n' -> promotionPiece = ChessPiece.PieceType.KNIGHT;
                }
            }
        }
        ChessPosition origin = convertToChessPosition(params[0]);
        ChessPosition destination = convertToChessPosition(params[1]);
        if (origin == null || destination == null) {
            return "Invalid Chess Position. Please format as follows:\n" +
                    "Letter (column) a-h followed by number 1-8 (row), e.g. f5";
        }
        ChessMove move = new ChessMove(origin, destination, promotionPiece);
        stateHandler.getWs().makeMove(stateHandler.getAuthToken(), gameData.gameID(), move);
        return "";
    }

    public String redrawBoard() {
        System.out.print("\u001b[0m");
        ChessBoardPrinter.highlightMoves(gameData.game().getBoard(),stateHandler.getPlayerColor().equals("WHITE"), null, null);
        return "";
    }

    public String resign() {
        System.out.print("Are you sure you want to resign? (yes/no): ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim().toLowerCase();

        if (!input.equals("yes")) {
            return "Resignation cancelled.";
        }
        stateHandler.getWs().resign(stateHandler.getAuthToken(), gameData.gameID());
        return "";
    }

    public String leave() {
        stateHandler.getWs().leaveGame(stateHandler.getAuthToken(), gameData.gameID());
        if (stateHandler.getPlayerColor() != null) {
            stateHandler.setPlayerColor(null);
        }
        stateHandler.setCurrentGameId(null);
        stateHandler.setState(State.SIGNEDIN);
        return "You have left the game";
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

    public ChessPosition convertToChessPosition(String input) {
        if (!Character.isLetter(input.charAt(0)) || !Character.isDigit(input.charAt(1))) {
            return null;
        }
        int row = Character.getNumericValue(input.charAt(1));
        char col = input.charAt(0);
        if (col > 'h' || row < 1 || row > 8) {
            return null;
        }
        int colNum = col - 'a' + 1;
        return new ChessPosition(row, colNum);
    }

    public void updateGameState(ChessGame updatedGame) {
        this.gameData = new GameData(gameData.gameID(), gameData.whiteUsername(), gameData.blackUsername(), gameData.gameName(), updatedGame);
    }
}
