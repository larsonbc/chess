package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import static ui.EscapeSequences.*;

public class ChessBoardPrinter {

    public static void printBoard(ChessBoard board, boolean whitePerspective) {
        printColumnLabels(whitePerspective);

        int startRow = whitePerspective ? 8 : 1;
        int endRow = whitePerspective ? 0 : 9;
        int stepRow = whitePerspective ? -1 : 1;

        for (int row = startRow; row != endRow; row += stepRow) {
            System.out.print(row + " ");  // Row label

            for (int col = 1; col <= 8; col++) {
                int file = whitePerspective ? col : 9 - col;

                ChessPosition position = new ChessPosition(row, file);
                ChessPiece piece = board.getPiece(position);

                boolean isLightSquare = (row + file) % 2 != 0;
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

        printColumnLabels(whitePerspective);
    }

    private static void printColumnLabels(boolean whitePerspective) {
        System.out.print("  ");
        if (whitePerspective) {
            for (char col = 'a'; col <= 'h'; col++) {
                System.out.print(" " + col + " ");
            }
        } else {
            for (char col = 'h'; col >= 'a'; col--) {
                System.out.print(" " + col + " ");
            }
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
