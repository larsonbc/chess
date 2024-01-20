package chess;

import java.util.ArrayList;
import java.util.Collection;

public class BishopMovesCalculator implements PieceMovesCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        ArrayList<ChessMove> bishopMoves = new ArrayList<ChessMove>();
        int currentRow = position.getRow();
        int currentCol = position.getColumn();
        //down and right
        while (currentRow < 8 && currentRow > 1 && currentCol < 8 & currentCol > 1) {
            currentRow -= 1;
            currentCol += 1;
        }
        while (currentRow != position.getRow()) {
            bishopMoves.add(new ChessMove(position, new ChessPosition(currentRow, currentCol), null));
            currentRow++;
            currentCol--;
        }
        //up and right
        currentRow = position.getRow();
        currentCol = position.getColumn();
        while (currentRow < 8 && currentRow > 1 && currentCol < 8 & currentCol > 1) {
            currentRow += 1;
            currentCol += 1;
        }
        while (currentRow != position.getRow()) {
            bishopMoves.add(new ChessMove(position, new ChessPosition(currentRow, currentCol), null));
            currentRow--;
            currentCol--;
        }
        //down and left
        currentRow = position.getRow();
        currentCol = position.getColumn();
        while (currentRow < 8 && currentRow > 1 && currentCol < 8 & currentCol > 1) {
            currentRow -= 1;
            currentCol -= 1;
        }
        while (currentRow != position.getRow()) {
            bishopMoves.add(new ChessMove(position, new ChessPosition(currentRow, currentCol), null));
            currentRow++;
            currentCol++;
        }
        //up and left
        currentRow = position.getRow();
        currentCol = position.getColumn();
        while (currentRow < 8 && currentRow > 1 && currentCol < 8 & currentCol > 1) {
            currentRow += 1;
            currentCol -= 1;
        }
        while (currentRow != position.getRow()) {
            bishopMoves.add(new ChessMove(position, new ChessPosition(currentRow, currentCol), null));
            currentRow--;
            currentCol++;
        }

        return bishopMoves;
    }
}
