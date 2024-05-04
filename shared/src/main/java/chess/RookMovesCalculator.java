package chess;

import java.util.ArrayList;
import java.util.Collection;

public class RookMovesCalculator implements PieceMovesCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        ArrayList<ChessMove> rookMoves = new ArrayList<>();
        ChessGame.TeamColor teamColor = board.getPiece(position).getTeamColor();
        int currentRow = position.getRow();
        int currentCol = position.getColumn();
        //up
        while (currentRow < 8) {
            currentRow++;
            if (board.getPiece(new ChessPosition(currentRow, currentCol)) != null) {
                if (board.getPiece(new ChessPosition(currentRow, currentCol)).getTeamColor() == teamColor) {
                    currentRow--;
                    break;
                } else {
                    break;
                }
            }
        }
        while (currentRow != position.getRow()) {
            rookMoves.add(new ChessMove(position, new ChessPosition(currentRow, currentCol), null));
            currentRow--;
        }
        //down
        while (currentRow > 1) {
            currentRow--;
            if (board.getPiece(new ChessPosition(currentRow, currentCol)) != null) {
                if (board.getPiece(new ChessPosition(currentRow, currentCol)).getTeamColor() == teamColor) {
                    currentRow++;
                    break;
                } else {
                    break;
                }
            }
        }
        while (currentRow != position.getRow()) {
            rookMoves.add(new ChessMove(position, new ChessPosition(currentRow, currentCol), null));
            currentRow++;
        }
        //right
        while (currentCol < 8) {
            currentCol++;
            if (board.getPiece(new ChessPosition(currentRow, currentCol)) != null) {
                if (board.getPiece(new ChessPosition(currentRow, currentCol)).getTeamColor() == teamColor) {
                    currentCol--;
                    break;
                } else {
                    break;
                }
            }
        }
        while (currentCol != position.getColumn()) {
            rookMoves.add(new ChessMove(position, new ChessPosition(currentRow, currentCol), null));
            currentCol--;
        }
        //left
        while (currentCol > 1) {
            currentCol--;
            if (board.getPiece(new ChessPosition(currentRow, currentCol)) != null) {
                if (board.getPiece(new ChessPosition(currentRow, currentCol)).getTeamColor() == teamColor) {
                    currentCol++;
                    break;
                } else {
                    break;
                }
            }
        }
        while (currentCol != position.getColumn()) {
            rookMoves.add(new ChessMove(position, new ChessPosition(currentRow, currentCol), null));
            currentCol++;
        }
        return rookMoves;
    }
}
