package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KnightMovesCalculator implements PieceMovesCalculator{
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        ChessGame.TeamColor teamColor = board.getPiece(position).getTeamColor();
        ArrayList<ChessMove> knightMoves = new ArrayList<>();
        int currentRow = position.getRow();
        int currentCol = position.getColumn();

        //up two right one
        if (currentRow + 2 < 9 && currentCol + 1 < 9 && (board.getPiece(new ChessPosition(currentRow + 2, currentCol + 1)) == null || board.getPiece(new ChessPosition(currentRow + 2, currentCol + 1)).getTeamColor() != teamColor)) {
            knightMoves.add(new ChessMove(position, new ChessPosition(currentRow + 2, currentCol + 1), null));
        }
        //down one left two
        if (currentRow - 1 > 0 && currentCol - 2 > 0 && (board.getPiece(new ChessPosition(currentRow - 1, currentCol - 2)) == null || board.getPiece(new ChessPosition(currentRow - 1, currentCol - 2)).getTeamColor() != teamColor)) {
            knightMoves.add(new ChessMove(position, new ChessPosition(currentRow - 1, currentCol - 2), null));
        }
        //up one left two
        if (currentRow + 1 < 9 && currentCol - 2 > 0 && (board.getPiece(new ChessPosition(currentRow + 1, currentCol - 2)) == null || board.getPiece(new ChessPosition(currentRow + 1, currentCol - 2)).getTeamColor() != teamColor)) {
            knightMoves.add(new ChessMove(position, new ChessPosition(currentRow + 1, currentCol - 2), null));
        }
        //up two left one
        if (currentRow + 2 < 9 && currentCol - 1 > 0 && (board.getPiece(new ChessPosition(currentRow + 2, currentCol - 1)) == null || board.getPiece(new ChessPosition(currentRow + 2, currentCol - 1)).getTeamColor() != teamColor)) {
            knightMoves.add(new ChessMove(position, new ChessPosition(currentRow + 2, currentCol - 1), null));
        }
        //down one right two
        if (currentRow - 1 > 0 && currentCol + 2 < 9 && (board.getPiece(new ChessPosition(currentRow - 1, currentCol + 2)) == null || board.getPiece(new ChessPosition(currentRow - 1, currentCol + 2)).getTeamColor() != teamColor)) {
            knightMoves.add(new ChessMove(position, new ChessPosition(currentRow - 1, currentCol + 2), null));
        }
        //down two right one
        if (currentRow - 2 > 0 && currentCol + 1 < 9 && (board.getPiece(new ChessPosition(currentRow - 2, currentCol + 1)) == null || board.getPiece(new ChessPosition(currentRow - 2, currentCol + 1)).getTeamColor() != teamColor)) {
            knightMoves.add(new ChessMove(position, new ChessPosition(currentRow - 2, currentCol + 1), null));
        }
        //up one right two
        if (currentRow + 1 < 9 && currentCol + 2 < 9 && (board.getPiece(new ChessPosition(currentRow + 1, currentCol + 2)) == null || board.getPiece(new ChessPosition(currentRow + 1, currentCol + 2)).getTeamColor() != teamColor)) {
            knightMoves.add(new ChessMove(position, new ChessPosition(currentRow + 1, currentCol + 2), null));
        }
        //down two left one
        if (currentRow - 2 > 0 && currentCol - 1 > 0 && (board.getPiece(new ChessPosition(currentRow - 2, currentCol - 1)) == null || board.getPiece(new ChessPosition(currentRow - 2, currentCol - 1)).getTeamColor() != teamColor)) {
            knightMoves.add(new ChessMove(position, new ChessPosition(currentRow - 2, currentCol - 1), null));
        }

        return knightMoves;
    }
}
