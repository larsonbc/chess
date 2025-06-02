package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import static ui.EscapeSequences.*;

public class ChessBoardPrinter {

    public static void printBoard(ChessBoard board, boolean whitePerspective) {
//        StringBuilder sb = new StringBuilder();
//        int[] rows = whitePerspective ? new int[]{8, 7, 6, 5, 4, 3, 2, 1} : new int[]{1, 2, 3, 4, 5, 6, 7, 8};
//        char[] cols = whitePerspective ? new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'}
//                : new char[]{'h', 'g', 'f', 'e', 'd', 'c', 'b', 'a'};
//
//        sb.append("  ");
//        for (char col : cols) {
//            sb.append("  ").append(col).append(" ");
//        }
//        sb.append("\n");
//
//        for (int row : rows) {
//            sb.append(row).append(" ");
//            for (char col : cols) {
//                int colNum = col - 'a' + 1;
//                ChessPiece piece = board.getPiece(new ChessPosition(row, colNum));
//                String symbol = getPieceSymbol(piece);
//                sb.append(" ").append(symbol).append(" ");
//            }
//            sb.append(" ").append(row).append("\n");
//        }
//        sb.append("  ");
//        for (char col : cols) {
//            sb.append(" ").append(col).append(" ");
//        }
//        sb.append("\n");
//        System.out.println(sb);
//    }
//
//    private static String getPieceSymbol(ChessPiece piece) {
//        if (piece == null) return ".";
//        char symbol;
//        switch (piece.getPieceType()) {
//            case KING -> symbol = 'K';
//            case QUEEN -> symbol = 'Q';
//            case ROOK -> symbol = 'R';
//            case BISHOP -> symbol = 'B';
//            case KNIGHT -> symbol = 'N';
//            case PAWN -> symbol = 'P';
//            default -> symbol = '?';
//        }
//        return piece.getTeamColor() == ChessGame.TeamColor.WHITE
//                ? String.valueOf(symbol)
//                : String.valueOf(Character.toLowerCase(symbol));

        printColumnLabels();

        for (int row = 8; row >= 1; row--) {
            System.out.print(row + " ");  // Row label
            for (int col = 1; col <= 8; col++) {
                ChessPosition position = new ChessPosition(row, col);
                ChessPiece piece = board.getPiece(position);

                boolean isLightSquare = (row + col) % 2 == 0;
                String bgColor = isLightSquare ? SET_BG_COLOR_LIGHT_GREY : SET_BG_COLOR_BLACK;

                String textColor;
                if (piece == null) {
                    textColor = SET_TEXT_COLOR_WHITE;
                } else {
                    textColor = (piece.getTeamColor() == ChessGame.TeamColor.WHITE)
                            ? SET_TEXT_COLOR_RED
                            : SET_TEXT_COLOR_BLUE;
                }

                String symbol = (piece == null) ? " " : getPieceLetter(piece);
                System.out.print(bgColor + textColor + " " + symbol + " " + RESET_TEXT_COLOR + RESET_BG_COLOR);
            }
            System.out.println(" " + row);
        }

        printColumnLabels();

    }

    private static void printColumnLabels() {
        System.out.print("  ");
        for (char col = 'a'; col <= 'h'; col++) {
            System.out.print(" " + col + " ");
        }
        System.out.println();
    }

    private static String getPieceLetter(ChessPiece piece) {
        return switch (piece.getPieceType()) {
            case KING -> "K";
            case QUEEN -> "Q";
            case BISHOP -> "B";
            case KNIGHT -> "N";
            case ROOK -> "R";
            case PAWN -> "P";
        };
    }
}
