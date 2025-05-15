package chess;

import java.util.ArrayList;

public class PawnMovesCalculator implements PieceMovesCalculator{
    @Override
    public ArrayList<ChessMove> calculateMoves(ChessBoard board, ChessPosition position) {
        ChessGame.TeamColor teamColor = board.getPiece(position).getTeamColor();
        ArrayList<ChessMove> pawnMoves = new ArrayList<>();
        int currentRow = position.getRow();
        int currentCol = position.getColumn();

        //White team
        if (teamColor == ChessGame.TeamColor.WHITE) {
            //forward one and two (first move)
            if (currentRow + 1 < 9 && board.getPiece(new ChessPosition(currentRow + 1, currentCol)) == null) {
                if (currentRow + 1 == 8) {
                    pawnMoves.add(new ChessMove(position, new ChessPosition(currentRow + 1, currentCol), ChessPiece.PieceType.QUEEN));
                    pawnMoves.add(new ChessMove(position, new ChessPosition(currentRow + 1, currentCol), ChessPiece.PieceType.ROOK));
                    pawnMoves.add(new ChessMove(position, new ChessPosition(currentRow + 1, currentCol), ChessPiece.PieceType.BISHOP));
                    pawnMoves.add(new ChessMove(position, new ChessPosition(currentRow + 1, currentCol), ChessPiece.PieceType.KNIGHT));
                } else {
                    pawnMoves.add(new ChessMove(position, new ChessPosition(currentRow + 1, currentCol), null));
                }
                if (currentRow == 2 && board.getPiece(new ChessPosition(currentRow + 2, currentCol)) == null) {
                    pawnMoves.add(new ChessMove(position, new ChessPosition(currentRow + 2, currentCol), null));
                }
            }
            //Diagonal up, left capture
            if (currentRow + 1 < 9 && currentCol - 1 > 0 && board.getPiece(new ChessPosition(currentRow + 1, currentCol - 1)) != null
                    && board.getPiece(new ChessPosition(currentRow + 1, currentCol - 1)).getTeamColor() != teamColor) {
                if (currentRow + 1 == 8) {
                    pawnMoves.add(new ChessMove(position, new ChessPosition(currentRow + 1, currentCol - 1), ChessPiece.PieceType.QUEEN));
                    pawnMoves.add(new ChessMove(position, new ChessPosition(currentRow + 1, currentCol - 1), ChessPiece.PieceType.ROOK));
                    pawnMoves.add(new ChessMove(position, new ChessPosition(currentRow + 1, currentCol - 1), ChessPiece.PieceType.BISHOP));
                    pawnMoves.add(new ChessMove(position, new ChessPosition(currentRow + 1, currentCol - 1), ChessPiece.PieceType.KNIGHT));
                } else {
                    pawnMoves.add(new ChessMove(position, new ChessPosition(currentRow + 1, currentCol - 1), null));
                }
            }
            //Diagonal up, right capture
            if (currentRow + 1 < 9 && currentCol + 1 < 9 && board.getPiece(new ChessPosition(currentRow + 1, currentCol + 1)) != null
                    && board.getPiece(new ChessPosition(currentRow + 1, currentCol + 1)).getTeamColor() != teamColor) {
                if (currentRow + 1 == 8) {
                    pawnMoves.add(new ChessMove(position, new ChessPosition(currentRow + 1, currentCol + 1), ChessPiece.PieceType.QUEEN));
                    pawnMoves.add(new ChessMove(position, new ChessPosition(currentRow + 1, currentCol + 1), ChessPiece.PieceType.ROOK));
                    pawnMoves.add(new ChessMove(position, new ChessPosition(currentRow + 1, currentCol + 1), ChessPiece.PieceType.BISHOP));
                    pawnMoves.add(new ChessMove(position, new ChessPosition(currentRow + 1, currentCol + 1), ChessPiece.PieceType.KNIGHT));
                } else {
                    pawnMoves.add(new ChessMove(position, new ChessPosition(currentRow + 1, currentCol + 1), null));
                }
            }

            //Black Team
        } else {
            if (currentRow - 1 > 0 && board.getPiece(new ChessPosition(currentRow - 1, currentCol)) == null) {
                if (currentRow - 1 == 1) {
                    pawnMoves.add(new ChessMove(position, new ChessPosition(currentRow - 1, currentCol), ChessPiece.PieceType.QUEEN));
                    pawnMoves.add(new ChessMove(position, new ChessPosition(currentRow - 1, currentCol), ChessPiece.PieceType.ROOK));
                    pawnMoves.add(new ChessMove(position, new ChessPosition(currentRow - 1, currentCol), ChessPiece.PieceType.BISHOP));
                    pawnMoves.add(new ChessMove(position, new ChessPosition(currentRow - 1, currentCol), ChessPiece.PieceType.KNIGHT));
                } else {
                    pawnMoves.add(new ChessMove(position, new ChessPosition(currentRow - 1, currentCol), null));
                }

                if (currentRow == 7 && board.getPiece(new ChessPosition(currentRow - 2, currentCol)) == null) {
                    pawnMoves.add(new ChessMove(position, new ChessPosition(currentRow - 2, currentCol), null));
                }
            }
            //Diagonal down, left capture
            if (currentRow - 1 > 0 && currentCol - 1 > 0 && board.getPiece(new ChessPosition(currentRow - 1, currentCol - 1)) != null
                    && board.getPiece(new ChessPosition(currentRow - 1, currentCol - 1)).getTeamColor() != teamColor) {
                if (currentRow - 1 == 1) {
                    pawnMoves.add(new ChessMove(position, new ChessPosition(currentRow - 1, currentCol - 1), ChessPiece.PieceType.QUEEN));
                    pawnMoves.add(new ChessMove(position, new ChessPosition(currentRow - 1, currentCol - 1), ChessPiece.PieceType.ROOK));
                    pawnMoves.add(new ChessMove(position, new ChessPosition(currentRow - 1, currentCol - 1), ChessPiece.PieceType.BISHOP));
                    pawnMoves.add(new ChessMove(position, new ChessPosition(currentRow - 1, currentCol - 1), ChessPiece.PieceType.KNIGHT));
                } else {
                    pawnMoves.add(new ChessMove(position, new ChessPosition(currentRow - 1, currentCol - 1), null));
                }
            }
            //Diagonal down, right capture
            if (currentRow - 1 > 0 && currentCol + 1 < 9 && board.getPiece(new ChessPosition(currentRow - 1, currentCol + 1)) != null
                    && board.getPiece(new ChessPosition(currentRow - 1, currentCol + 1)).getTeamColor() != teamColor) {
                if (currentRow - 1 == 1) {
                    pawnMoves.add(new ChessMove(position, new ChessPosition(currentRow - 1, currentCol + 1), ChessPiece.PieceType.QUEEN));
                    pawnMoves.add(new ChessMove(position, new ChessPosition(currentRow - 1, currentCol + 1), ChessPiece.PieceType.ROOK));
                    pawnMoves.add(new ChessMove(position, new ChessPosition(currentRow - 1, currentCol + 1), ChessPiece.PieceType.BISHOP));
                    pawnMoves.add(new ChessMove(position, new ChessPosition(currentRow - 1, currentCol + 1), ChessPiece.PieceType.KNIGHT));
                } else {
                    pawnMoves.add(new ChessMove(position, new ChessPosition(currentRow - 1, currentCol + 1), null));
                }
            }
        }

        return pawnMoves;
    }
}
