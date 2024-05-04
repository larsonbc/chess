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
        while (currentRow < 8 && currentCol < 8) {
            currentRow++;
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
            rookMoves.add(new ChessMove(position, new ChessPosition(currentRow, currentCol), null));
            currentRow--;
        }
        return rookMoves;
    }
}
