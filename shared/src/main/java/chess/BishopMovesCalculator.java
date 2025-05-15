package chess;

import java.util.ArrayList;

public class BishopMovesCalculator implements  PieceMovesCalculator{
    @Override
    public ArrayList<ChessMove> calculateMoves(ChessBoard board, ChessPosition position) {
        ArrayList<ChessMove> bishopMoves  = new ArrayList<>();
        ChessPiece piece = board.getPiece(position);
        int row = position.getRow();
        int col = position.getColumn();

        //up right
        while (row < 8 && col < 8) {
            if (board.isVacant(row + 1, col + 1)) {
                bishopMoves.add(new ChessMove(position, new ChessPosition(row + 1, col + 1), null));
                row++;
                col++;
            } else {
                if (board.isOpponent(row + 1, col + 1, piece)) {
                    bishopMoves.add(new ChessMove(position, new ChessPosition(row + 1, col + 1), null));
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
                bishopMoves.add(new ChessMove(position, new ChessPosition(row - 1, col + 1), null));
                row--;
                col++;
            } else {
                if (board.isOpponent(row - 1, col + 1, piece)) {
                    bishopMoves.add(new ChessMove(position, new ChessPosition(row - 1, col + 1), null));
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
                bishopMoves.add(new ChessMove(position, new ChessPosition(row - 1, col - 1), null));
                row--;
                col--;
            } else {
                if (board.isOpponent(row - 1, col - 1, piece)) {
                    bishopMoves.add(new ChessMove(position, new ChessPosition(row - 1, col - 1), null));
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
                bishopMoves.add(new ChessMove(position, new ChessPosition(row + 1, col - 1), null));
                row++;
                col--;
            } else {
                if (board.isOpponent(row + 1, col - 1, piece)) {
                    bishopMoves.add(new ChessMove(position, new ChessPosition(row + 1, col - 1), null));
                    break;
                } else {
                    break;
                }
            }
        }

        return bishopMoves;
    }
}
