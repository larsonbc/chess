package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.Collection;

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


//    public static void highlightMoves(ChessBoard board, boolean whitePerspective,
//                                      Collection<ChessPosition> highlights, ChessPosition origin) {
//        printColumnLabels(whitePerspective);
//
//        int startRow = whitePerspective ? 8 : 1;
//        int endRow = whitePerspective ? 0 : 9;
//        int stepRow = whitePerspective ? -1 : 1;
//
//        for (int row = startRow; row != endRow; row += stepRow) {
//            System.out.print(row + " ");  // Row label
//
//            for (int col = 1; col <= 8; col++) {
//                int file = whitePerspective ? col : 9 - col;
//
//                ChessPosition position = new ChessPosition(row, file);
//                ChessPiece piece = board.getPiece(position);
//
//                boolean isHighlighted = highlights != null && highlights.contains(position);
//                boolean isOrigin = origin != null && origin.equals(position);
//                boolean isLightSquare = (row + file) % 2 != 0;
//
//                String bgColor;
//                if (isOrigin) {
//                    bgColor = SET_BG_COLOR_YELLOW;
//                } else if (isHighlighted) {
//                    bgColor = SET_BG_COLOR_GREEN;
//                } else {
//                    bgColor = isLightSquare ? SET_BG_COLOR_LIGHT_GREY : SET_BG_COLOR_BLACK;
//                }
//
//                String textColor = piece == null
//                        ? SET_TEXT_COLOR_WHITE
//                        : (piece.getTeamColor() == ChessGame.TeamColor.WHITE ? SET_TEXT_COLOR_RED : SET_TEXT_COLOR_BLUE);
//
//                String symbol = (piece == null) ? " " : getPieceLetter(piece);
//                System.out.print(bgColor + textColor + " " + symbol + " " + RESET_TEXT_COLOR + RESET_BG_COLOR);
//            }
//
//            System.out.println(" " + row);
//        }
//
//        printColumnLabels(whitePerspective);
//    }


//    public static void highlightMoves(ChessBoard board, boolean whitePerspective,
//                                      ChessPosition origin, Collection<ChessPosition> destinations) {
//        printColumnLabels(whitePerspective);
//
//        int startRow = whitePerspective ? 8 : 1;
//        int endRow = whitePerspective ? 0 : 9;
//        int stepRow = whitePerspective ? -1 : 1;
//
//        for (int row = startRow; row != endRow; row += stepRow) {
//            System.out.print(row + " ");
//
//            for (int col = 1; col <= 8; col++) {
//                int file = whitePerspective ? col : 9 - col;
//                ChessPosition position = new ChessPosition(row, file);
//                ChessPiece piece = board.getPiece(position);
//
//                boolean isOrigin = origin != null && position.equals(origin);
//                boolean isDestination = destinations != null && destinations.contains(position);
//
//                String bgColor;
//                if (isOrigin || isDestination) {
//                    bgColor = SET_BG_COLOR_MAGENTA; // unified highlight for both
//                } else {
//                    boolean isLightSquare = (row + file) % 2 != 0;
//                    bgColor = isLightSquare ? SET_BG_COLOR_LIGHT_GREY : SET_BG_COLOR_BLACK;
//                }
//
//                String textColor = (piece == null)
//                        ? SET_TEXT_COLOR_WHITE
//                        : (piece.getTeamColor() == ChessGame.TeamColor.WHITE
//                        ? SET_TEXT_COLOR_RED
//                        : SET_TEXT_COLOR_BLUE);
//
//                String symbol = (piece == null) ? " " : getPieceLetter(piece);
//                System.out.print(bgColor + textColor + " " + symbol + " " + RESET_TEXT_COLOR + RESET_BG_COLOR);
//            }
//
//            System.out.println(" " + row);
//        }
//
//        printColumnLabels(whitePerspective);
//    }



    public static void highlightMoves(
            ChessBoard board,
            boolean whitePerspective,
            ChessPosition selectedOrigin,                   // yellow (currently selected piece)
            Collection<ChessPosition> legalDestinations,    // green (valid moves for selected piece)
            ChessPosition lastMoveOrigin,                   // magenta (last move origin)
            ChessPosition lastMoveDestination               // magenta (last move destination)
    ) {
        printColumnLabels(whitePerspective);

        int startRow = whitePerspective ? 8 : 1;
        int endRow = whitePerspective ? 0 : 9;
        int stepRow = whitePerspective ? -1 : 1;

        for (int row = startRow; row != endRow; row += stepRow) {
            System.out.print(row + " ");

            for (int col = 1; col <= 8; col++) {
                int file = whitePerspective ? col : 9 - col;
                ChessPosition position = new ChessPosition(row, file);
                ChessPiece piece = board.getPiece(position);

                // Flags for square status
                boolean isLastMoveOrigin = lastMoveOrigin != null && position.equals(lastMoveOrigin);
                boolean isLastMoveDestination = lastMoveDestination != null && position.equals(lastMoveDestination);
                boolean isSelectedOrigin = selectedOrigin != null && position.equals(selectedOrigin);
                boolean isLegalDestination = legalDestinations != null && legalDestinations.contains(position);

                // Determine background color
                String bgColor;
                if (isLastMoveOrigin || isLastMoveDestination) {
                    bgColor = SET_BG_COLOR_MAGENTA;
                } else if (isSelectedOrigin) {
                    bgColor = SET_BG_COLOR_YELLOW;
                } else if (isLegalDestination) {
                    bgColor = SET_BG_COLOR_GREEN;
                } else {
                    boolean isLightSquare = (row + file) % 2 != 0;
                    bgColor = isLightSquare ? SET_BG_COLOR_LIGHT_GREY : SET_BG_COLOR_BLACK;
                }

                // Text color depends on piece color or empty
                String textColor = (piece == null)
                        ? SET_TEXT_COLOR_WHITE
                        : (piece.getTeamColor() == ChessGame.TeamColor.WHITE
                        ? SET_TEXT_COLOR_RED
                        : SET_TEXT_COLOR_BLUE);

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
