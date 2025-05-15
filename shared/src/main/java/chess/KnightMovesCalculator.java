package chess;

import java.util.ArrayList;

public class KnightMovesCalculator implements PieceMovesCalculator{
    @Override
    public ArrayList<ChessMove> calculateMoves(ChessBoard board, ChessPosition position) {
        ArrayList<ChessMove> knightMoves = new ArrayList<>();
        ChessPiece piece = board.getPiece(position);
        int row = position.getRow();
        int col = position.getColumn();

        //up two right one
        if (row + 2 < 9 && col + 1 < 9 && (board.isVacant(row + 2, col + 1) || board.isOpponent(row + 2, col + 1, piece))) {
            knightMoves.add(new ChessMove(position, new ChessPosition(row + 2, col + 1), null));
        }
        //down one left two
        if (row - 1 > 0 && col - 2 > 0 && (board.isVacant(row - 1, col - 2) || board.isOpponent(row - 1, col - 2, piece))) {
            knightMoves.add(new ChessMove(position, new ChessPosition(row - 1, col - 2), null));
        }
        //up one left two
        if (row + 1 < 9 && col - 2 > 0 && (board.isVacant(row + 1, col - 2) || board.isOpponent(row + 1, col - 2, piece))) {
            knightMoves.add(new ChessMove(position, new ChessPosition(row + 1, col - 2), null));
        }
        //up two left one
        if (row + 2 < 9 && col - 1 > 0 && (board.isVacant(row + 2, col - 1) || board.isOpponent(row + 2, col - 1, piece))) {
            knightMoves.add(new ChessMove(position, new ChessPosition(row + 2, col - 1), null));
        }
        //down one right two
        if (row - 1 > 0 && col + 2 < 9 && (board.isVacant(row - 1, col + 2) || board.isOpponent(row - 1, col + 2, piece))) {
            knightMoves.add(new ChessMove(position, new ChessPosition(row - 1, col + 2), null));
        }
        //down two right one
        if (row - 2 > 0 && col + 1 < 9 && (board.isVacant(row - 2, col + 1) || board.isOpponent(row - 2, col + 1, piece))) {
            knightMoves.add(new ChessMove(position, new ChessPosition(row - 2, col + 1), null));
        }
        //up one right two
        if (row + 1 < 9 && col + 2 < 9 && (board.isVacant(row + 1, col + 2) || board.isOpponent(row + 1, col + 2, piece))) {
            knightMoves.add(new ChessMove(position, new ChessPosition(row + 1, col + 2), null));
        }
        //down two left one
        if (row - 2 > 0 && col - 1 > 0 && (board.isVacant(row - 2, col - 1) || board.isOpponent(row - 2, col - 1, piece))) {
            knightMoves.add(new ChessMove(position, new ChessPosition(row - 2, col - 1), null));
        }

        return knightMoves;
    }
}
