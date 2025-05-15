package chess;

import java.util.ArrayList;

public interface PieceMovesCalculator {
    ArrayList<ChessMove> calculateMoves(ChessBoard board, ChessPosition position);
}
