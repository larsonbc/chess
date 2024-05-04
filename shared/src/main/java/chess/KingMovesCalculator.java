package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KingMovesCalculator implements PieceMovesCalculator{
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        ChessGame.TeamColor teamColor = board.getPiece(position).getTeamColor();
        ArrayList<ChessMove> kingMoves = new ArrayList<>();
        int currentRow = position.getRow();
        int currentCol = position.getColumn();
        //up
        if ((currentRow + 1 < 9) && (board.getPiece(new ChessPosition(currentRow + 1, currentCol)) == null)) {
            kingMoves.add(new ChessMove(position, new ChessPosition(currentRow + 1, currentCol), null));
        } else {
            if ((currentRow + 1 < 9) && (board.getPiece(new ChessPosition(currentRow + 1, currentCol)).getTeamColor() != teamColor)) {
                kingMoves.add(new ChessMove(position, new ChessPosition(currentRow + 1, currentCol), null));
            }
        }
        //left
        if ((currentCol - 1 > 0) && (board.getPiece(new ChessPosition(currentRow, currentCol - 1)) == null)) {
            kingMoves.add(new ChessMove(position, new ChessPosition(currentRow, currentCol - 1), null));
        } else {
            if ((currentCol - 1 > 0) && board.getPiece(new ChessPosition(currentRow, currentCol - 1)).getTeamColor() != teamColor) {
                kingMoves.add(new ChessMove(position, new ChessPosition(currentRow, currentCol - 1), null));
            }
        }
        //left and up
        if ((currentCol - 1 > 0) && (currentRow + 1 < 9) && (board.getPiece(new ChessPosition(currentRow + 1, currentCol - 1)) == null)) {
            kingMoves.add(new ChessMove(position, new ChessPosition(currentRow + 1, currentCol - 1), null));
        } else {
            if ((currentCol - 1 > 0) && (currentRow + 1 < 9) && board.getPiece(new ChessPosition(currentRow + 1, currentCol - 1)).getTeamColor() != teamColor) {
                kingMoves.add(new ChessMove(position, new ChessPosition(currentRow + 1, currentCol - 1), null));
            }
        }
        //down and right
        if ((currentCol + 1 < 9) && (currentRow - 1 > 0) && (board.getPiece(new ChessPosition(currentRow - 1, currentCol + 1)) == null)) {
            kingMoves.add(new ChessMove(position, new ChessPosition(currentRow - 1, currentCol + 1), null));
        } else {
            if ((currentCol + 1 < 9) && (currentRow - 1 > 0) && board.getPiece(new ChessPosition(currentRow - 1, currentCol + 1)).getTeamColor() != teamColor) {
                kingMoves.add(new ChessMove(position, new ChessPosition(currentRow - 1, currentCol + 1), null));
            }
        }
        //right
        if ((currentCol + 1 < 9) && (board.getPiece(new ChessPosition(currentRow, currentCol + 1)) == null)) {
            kingMoves.add(new ChessMove(position, new ChessPosition(currentRow, currentCol + 1), null));
        } else {
            if ((currentCol + 1 < 9) && board.getPiece(new ChessPosition(currentRow, currentCol + 1)).getTeamColor() != teamColor) {
                kingMoves.add(new ChessMove(position, new ChessPosition(currentRow, currentCol + 1), null));
            }
        }
        //down
        if ((currentRow - 1 > 0) && (board.getPiece(new ChessPosition(currentRow - 1, currentCol)) == null)) {
            kingMoves.add(new ChessMove(position, new ChessPosition(currentRow - 1, currentCol), null));
        } else {
            if ((currentRow - 1 > 0) && board.getPiece(new ChessPosition(currentRow - 1, currentCol)).getTeamColor() != teamColor) {
                kingMoves.add(new ChessMove(position, new ChessPosition(currentRow - 1, currentCol), null));
            }
        }
        //up and right
        if ((currentRow + 1 < 9) && (currentCol + 1 < 9) && (board.getPiece(new ChessPosition(currentRow + 1, currentCol + 1)) == null)) {
            kingMoves.add(new ChessMove(position, new ChessPosition(currentRow + 1, currentCol + 1), null));
        } else {
            if ((currentRow + 1 < 9) && (currentCol + 1 < 9) && board.getPiece(new ChessPosition(currentRow + 1, currentCol + 1)).getTeamColor() != teamColor) {
                kingMoves.add(new ChessMove(position, new ChessPosition(currentRow + 1, currentCol + 1), null));
            }
        }
        //down and left
        if ((currentRow - 1 > 0) && (currentCol - 1 > 0) && (board.getPiece(new ChessPosition(currentRow - 1, currentCol - 1)) == null)) {
            kingMoves.add(new ChessMove(position, new ChessPosition(currentRow - 1, currentCol - 1), null));
        } else {
            if ((currentRow - 1 > 0) && (currentCol - 1 > 0) && board.getPiece(new ChessPosition(currentRow - 1, currentCol - 1)).getTeamColor() != teamColor) {
                kingMoves.add(new ChessMove(position, new ChessPosition(currentRow - 1, currentCol - 1), null));
            }
        }

        return kingMoves;
    }
}
