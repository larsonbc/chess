package chess;

import java.io.ObjectInputFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BishopMovesCalculator implements PieceMovesCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        //bishopMoves.add(new ChessMove(position, new ChessPosition(1,1), null));
        //System.out.println(board.isVacant(new ChessPosition(currentRow, currentColl)));
        ArrayList<ChessMove> bishopMoves = new ArrayList<>();
        //down and right
        int currentRow = position.getRow();
        int currentCol = position.getColumn();
        while (currentRow > 1 && currentCol < 8) {
            currentRow--;
            currentCol++;
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
        }
        while (currentRow != position.getRow()) {
            bishopMoves.add(new ChessMove(position, new ChessPosition(currentRow, currentCol), null));
            currentRow--;
            currentCol++;
        }


        //System.out.println(bishopMoves);
        return bishopMoves;
    }
}
