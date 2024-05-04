package chess;

import java.util.ArrayList;
import java.util.Collection;

public class BishopMovesCalculator implements PieceMovesCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        ChessGame.TeamColor teamColor = board.getPiece(position).getTeamColor();
        ArrayList<ChessMove> bishopMoves = new ArrayList<>();
        //down and right
        int currentRow = position.getRow();
        int currentCol = position.getColumn();
        while (currentRow > 1 && currentCol < 8) {
            currentRow--;
            currentCol++;
            if (board.getPiece(new ChessPosition(currentRow, currentCol)) != null) {
                if (board.getPiece(new ChessPosition(currentRow, currentCol)).getTeamColor() == teamColor) {
                    currentRow++;
                    currentCol--;
                    break;
                } else {
                    break;
                }
            }
        }
        while (currentRow != position.getRow()) {
            bishopMoves.add(new ChessMove(position, new ChessPosition(currentRow, currentCol), null));
            currentRow++;
            currentCol--;
        }
        //up and right
        currentRow = position.getRow();
        currentCol = position.getColumn();
        while (currentRow < 8 && currentCol < 8) {
            currentRow++;
            currentCol++;
            if (board.getPiece(new ChessPosition(currentRow, currentCol)) != null) {
                if (board.getPiece(new ChessPosition(currentRow, currentCol)).getTeamColor() == teamColor) {
                    currentRow--;
                    currentCol--;
                    break;
                } else {
                    break;
                }
            }
        }
        while (currentRow != position.getRow()) {
            bishopMoves.add(new ChessMove(position, new ChessPosition(currentRow, currentCol), null));
            currentRow--;
            currentCol--;
        }
        //down and left
        currentRow = position.getRow();
        currentCol = position.getColumn();
        while (currentRow > 1 && currentCol > 1) {
            currentRow--;
            currentCol--;
            if (board.getPiece(new ChessPosition(currentRow, currentCol)) != null) {
                if (board.getPiece(new ChessPosition(currentRow, currentCol)).getTeamColor() == teamColor) {
                    currentRow++;
                    currentCol++;
                    break;
                } else {
                    break;
                }
            }
        }
        while (currentRow != position.getRow()) {
            bishopMoves.add(new ChessMove(position, new ChessPosition(currentRow, currentCol), null));
            currentRow++;
            currentCol++;
        }
        //up and left
        while (currentRow < 8 && currentCol > 1) {
            currentRow++;
            currentCol--;
            if (board.getPiece(new ChessPosition(currentRow, currentCol)) != null) {
                if (board.getPiece(new ChessPosition(currentRow, currentCol)).getTeamColor() == teamColor) {
                    currentRow--;
                    currentCol++;
                    break;
                } else {
                    break;
                }
            }
        }
        while (currentRow != position.getRow()) {
            bishopMoves.add(new ChessMove(position, new ChessPosition(currentRow, currentCol), null));
            currentRow--;
            currentCol++;
        }
        return bishopMoves;
    }
}
