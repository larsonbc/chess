package chess;

import java.util.ArrayList;

public class KingMovesCalculator implements PieceMovesCalculator{
    @Override
    public ArrayList<ChessMove> calculateMoves(ChessBoard board, ChessPosition position) {
        ArrayList<ChessMove> kingMoves = new ArrayList<>();
        ChessPiece piece = board.getPiece(position);
        int row = position.getRow();
        int col = position.getColumn();

        //up
        if (row + 1 < 9 && (board.isVacant(row + 1, col) || board.isOpponent(row + 1, col, piece))) {
            kingMoves.add(new ChessMove(position, new ChessPosition(row + 1, col), null));
        }
        //up right
        if (row + 1 < 9 && col + 1 < 9 && (board.isVacant(row + 1, col + 1) || board.isOpponent(row + 1, col + 1, piece))) {
            kingMoves.add(new ChessMove(position, new ChessPosition(row + 1, col + 1), null));
        }
        //right
        if (col + 1 < 9 && (board.isVacant(row, col + 1) || board.isOpponent(row, col + 1, piece))) {
            kingMoves.add(new ChessMove(position, new ChessPosition(row, col + 1), null));
        }
        //down right
        if (row - 1 > 0 && col + 1 < 9 && (board.isVacant(row - 1, col + 1) || board.isOpponent(row - 1, col + 1, piece))) {
            kingMoves.add(new ChessMove(position, new ChessPosition(row - 1, col + 1), null));
        }
        //down
        if (row - 1 > 0 && (board.isVacant(row - 1, col) || board.isOpponent(row - 1, col, piece))) {
            kingMoves.add(new ChessMove(position, new ChessPosition(row - 1, col), null));
        }
        //down left
        if (row - 1 > 0 && col - 1 > 0 && (board.isVacant(row - 1, col - 1) || board.isOpponent(row - 1, col - 1, piece))) {
            kingMoves.add(new ChessMove(position, new ChessPosition(row - 1, col - 1), null));
        }
        //left
        if (col - 1 > 0 && (board.isVacant(row, col - 1) || board.isOpponent(row, col - 1, piece))) {
            kingMoves.add(new ChessMove(position, new ChessPosition(row, col - 1), null));
        }
        //up left
        if (row + 1 < 9 && col - 1 > 0 && (board.isVacant(row + 1, col - 1) || board.isOpponent(row + 1, col - 1, piece))) {
            kingMoves.add(new ChessMove(position, new ChessPosition(row + 1, col - 1), null));
        }

        return kingMoves;
    }
}
