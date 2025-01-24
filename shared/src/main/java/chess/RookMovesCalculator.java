package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RookMovesCalculator implements PieceMovesCalculator{
    @Override
    public Collection<ChessMove> calculateMoves(ChessBoard board, ChessPosition position) {
        ArrayList<ChessMove> rookMoves = new ArrayList<>();
        ChessPiece piece = board.getPiece(position);
        int row = position.getRow();
        int col = position.getColumn();

        //up
        while (row < 8) {
            if (board.isVacant(row + 1, col)) {
                rookMoves.add(new ChessMove(position, new ChessPosition(row + 1, col), null));
                row++;
            } else {
                if (board.isOpponent(row + 1, col, piece)) {
                    rookMoves.add(new ChessMove(position, new ChessPosition(row + 1, col), null));
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
                rookMoves.add(new ChessMove(position, new ChessPosition(row, col + 1), null));
                col++;
            } else {
                if (board.isOpponent(row, col + 1, piece)) {
                    rookMoves.add(new ChessMove(position, new ChessPosition(row, col + 1), null));
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
                rookMoves.add(new ChessMove(position, new ChessPosition(row - 1, col), null));
                row--;
            } else {
                if (board.isOpponent(row - 1, col, piece)) {
                    rookMoves.add(new ChessMove(position, new ChessPosition(row - 1, col), null));
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
                rookMoves.add(new ChessMove(position, new ChessPosition(row, col - 1), null));
                col--;
            } else {
                if (board.isOpponent(row, col - 1, piece)) {
                    rookMoves.add(new ChessMove(position, new ChessPosition(row, col - 1), null));
                    break;
                } else {
                    break;
                }
            }
        }

        return rookMoves;
    }
}
