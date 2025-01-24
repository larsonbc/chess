package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class QueenMovesCalculator implements PieceMovesCalculator{
    @Override
    public Collection<ChessMove> calculateMoves(ChessBoard board, ChessPosition position) {
        ArrayList<ChessMove> queenMoves = new ArrayList<>();
        ChessPiece piece = board.getPiece(position);
        int row = position.getRow();
        int col = position.getColumn();

        //up right
        while (row < 8 && col < 8) {
            if (board.isVacant(row + 1, col + 1)) {
                queenMoves.add(new ChessMove(position, new ChessPosition(row + 1, col + 1), null));
                row++;
                col++;
            } else {
                if (board.isOpponent(row + 1, col + 1, piece)) {
                    queenMoves.add(new ChessMove(position, new ChessPosition(row + 1, col + 1), null));
                    break;
                } else {
                    break;
                }
            }
        }
        //down right
        row = position.getRow();
        col = position.getColumn();
        while (row > 1 && col < 8) {
            if (board.isVacant(row - 1, col + 1)) {
                queenMoves.add(new ChessMove(position, new ChessPosition(row - 1, col + 1), null));
                row--;
                col++;
            } else {
                if (board.isOpponent(row - 1, col + 1, piece)) {
                    queenMoves.add(new ChessMove(position, new ChessPosition(row - 1, col + 1), null));
                    break;
                } else {
                    break;
                }
            }
        }
        //down left
        row = position.getRow();
        col = position.getColumn();
        while (row > 1 && col > 1) {
            if (board.isVacant(row - 1, col - 1)) {
                queenMoves.add(new ChessMove(position, new ChessPosition(row - 1, col - 1), null));
                row--;
                col--;
            } else {
                if (board.isOpponent(row - 1, col - 1, piece)) {
                    queenMoves.add(new ChessMove(position, new ChessPosition(row - 1, col - 1), null));
                    break;
                } else {
                    break;
                }
            }
        }
        //up left
        row = position.getRow();
        col = position.getColumn();
        while (row < 8 && col > 1) {
            if (board.isVacant(row + 1, col - 1)) {
                queenMoves.add(new ChessMove(position, new ChessPosition(row + 1, col - 1), null));
                row++;
                col--;
            } else {
                if (board.isOpponent(row + 1, col - 1, piece)) {
                    queenMoves.add(new ChessMove(position, new ChessPosition(row + 1, col - 1), null));
                    break;
                } else {
                    break;
                }
            }
        }

        //up
        row = position.getRow();
        col = position.getColumn();
        while (row < 8) {
            if (board.isVacant(row + 1, col)) {
                queenMoves.add(new ChessMove(position, new ChessPosition(row + 1, col), null));
                row++;
            } else {
                if (board.isOpponent(row + 1, col, piece)) {
                    queenMoves.add(new ChessMove(position, new ChessPosition(row + 1, col), null));
                    break;
                } else {
                    break;
                }
            }
        }
        //right
        row = position.getRow();
        col = position.getColumn();
        while (col < 8) {
            if (board.isVacant(row, col + 1)) {
                queenMoves.add(new ChessMove(position, new ChessPosition(row, col + 1), null));
                col++;
            } else {
                if (board.isOpponent(row, col + 1, piece)) {
                    queenMoves.add(new ChessMove(position, new ChessPosition(row, col + 1), null));
                    break;
                } else {
                    break;
                }
            }
        }
        //down
        row = position.getRow();
        col = position.getColumn();
        while (row > 1) {
            if (board.isVacant(row - 1, col)) {
                queenMoves.add(new ChessMove(position, new ChessPosition(row - 1, col), null));
                row--;
            } else {
                if (board.isOpponent(row - 1, col, piece)) {
                    queenMoves.add(new ChessMove(position, new ChessPosition(row - 1, col), null));
                    break;
                } else {
                    break;
                }
            }
        }
        //left
        row = position.getRow();
        col = position.getColumn();
        while (col > 1) {
            if (board.isVacant(row, col - 1)) {
                queenMoves.add(new ChessMove(position, new ChessPosition(row, col - 1), null));
                col--;
            } else {
                if (board.isOpponent(row, col - 1, piece)) {
                    queenMoves.add(new ChessMove(position, new ChessPosition(row, col - 1), null));
                    break;
                } else {
                    break;
                }
            }
        }

        return queenMoves;
    }
}
