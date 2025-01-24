package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PawnMovesCalculator implements PieceMovesCalculator{
    @Override
    public Collection<ChessMove> calculateMoves(ChessBoard board, ChessPosition position) {
        ArrayList<ChessMove> pawnMoves = new ArrayList<>();
        ChessPiece piece = board.getPiece(position);
        int row = position.getRow();
        int col = position.getColumn();

        //White Team
        if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
            if (row + 1 < 9 && board.isVacant(row + 1, col)) {
                if (row == 7) {
                    pawnMoves.add(new ChessMove(position, new ChessPosition(row + 1, col), ChessPiece.PieceType.QUEEN));
                    pawnMoves.add(new ChessMove(position, new ChessPosition(row + 1, col), ChessPiece.PieceType.BISHOP));
                    pawnMoves.add(new ChessMove(position, new ChessPosition(row + 1, col), ChessPiece.PieceType.ROOK));
                    pawnMoves.add(new ChessMove(position, new ChessPosition(row + 1, col), ChessPiece.PieceType.KNIGHT));
                }
                else {
                    pawnMoves.add(new ChessMove(position, new ChessPosition(row + 1, col), null));
                }
                if (row == 2 && board.isVacant(row + 2, col)) {
                    pawnMoves.add(new ChessMove(position, new ChessPosition(row + 2, col), null));
                }
            }
            //diagonal right capture
            if (row + 1 < 9 && col + 1 < 9 && board.isOpponent(row + 1, col + 1, piece)) {
                if (row == 7) {
                    pawnMoves.add(new ChessMove(position, new ChessPosition(row + 1, col + 1), ChessPiece.PieceType.QUEEN));
                    pawnMoves.add(new ChessMove(position, new ChessPosition(row + 1, col + 1), ChessPiece.PieceType.BISHOP));
                    pawnMoves.add(new ChessMove(position, new ChessPosition(row + 1, col + 1), ChessPiece.PieceType.ROOK));
                    pawnMoves.add(new ChessMove(position, new ChessPosition(row + 1, col + 1), ChessPiece.PieceType.KNIGHT));
                } else {
                    pawnMoves.add(new ChessMove(position, new ChessPosition(row + 1, col + 1), null));
                }
            }
            //diagonal left capture
            if (row + 1 < 9 && col - 1 > 0 && board.isOpponent(row + 1, col - 1, piece)) {
                if (row == 7) {
                    pawnMoves.add(new ChessMove(position, new ChessPosition(row + 1, col - 1), ChessPiece.PieceType.QUEEN));
                    pawnMoves.add(new ChessMove(position, new ChessPosition(row + 1, col - 1), ChessPiece.PieceType.BISHOP));
                    pawnMoves.add(new ChessMove(position, new ChessPosition(row + 1, col - 1), ChessPiece.PieceType.ROOK));
                    pawnMoves.add(new ChessMove(position, new ChessPosition(row + 1, col - 1), ChessPiece.PieceType.KNIGHT));
                } else {
                    pawnMoves.add(new ChessMove(position, new ChessPosition(row + 1, col - 1), null));
                }
            }
        }

        //Black Team
        if (piece.getTeamColor() == ChessGame.TeamColor.BLACK) {
            if (row - 1 > 0 && board.isVacant(row - 1, col)) {
                if (row == 2) {
                    pawnMoves.add(new ChessMove(position, new ChessPosition(row - 1, col), ChessPiece.PieceType.QUEEN));
                    pawnMoves.add(new ChessMove(position, new ChessPosition(row - 1, col), ChessPiece.PieceType.BISHOP));
                    pawnMoves.add(new ChessMove(position, new ChessPosition(row - 1, col), ChessPiece.PieceType.ROOK));
                    pawnMoves.add(new ChessMove(position, new ChessPosition(row - 1, col), ChessPiece.PieceType.KNIGHT));
                }
                else {
                    pawnMoves.add(new ChessMove(position, new ChessPosition(row - 1, col), null));
                }
                if (row == 7 && board.isVacant(row - 2, col)) {
                    pawnMoves.add(new ChessMove(position, new ChessPosition(row - 2, col), null));
                }
            }
            //diagonal right capture
            if (row - 1 > 0 && col + 1 < 9 && board.isOpponent(row - 1, col + 1, piece)) {
                if (row == 7) {
                    pawnMoves.add(new ChessMove(position, new ChessPosition(row - 1, col + 1), ChessPiece.PieceType.QUEEN));
                    pawnMoves.add(new ChessMove(position, new ChessPosition(row - 1, col + 1), ChessPiece.PieceType.BISHOP));
                    pawnMoves.add(new ChessMove(position, new ChessPosition(row - 1, col + 1), ChessPiece.PieceType.ROOK));
                    pawnMoves.add(new ChessMove(position, new ChessPosition(row - 1, col + 1), ChessPiece.PieceType.KNIGHT));
                } else {
                    pawnMoves.add(new ChessMove(position, new ChessPosition(row - 1, col + 1), null));
                }
            }
            //diagonal left capture
            if (row - 1 > 0 && col - 1 > 0 && board.isOpponent(row - 1, col - 1, piece)) {
                if (row == 2) {
                    pawnMoves.add(new ChessMove(position, new ChessPosition(row - 1, col - 1), ChessPiece.PieceType.QUEEN));
                    pawnMoves.add(new ChessMove(position, new ChessPosition(row - 1, col - 1), ChessPiece.PieceType.BISHOP));
                    pawnMoves.add(new ChessMove(position, new ChessPosition(row - 1, col - 1), ChessPiece.PieceType.ROOK));
                    pawnMoves.add(new ChessMove(position, new ChessPosition(row - 1, col - 1), ChessPiece.PieceType.KNIGHT));
                } else {
                    pawnMoves.add(new ChessMove(position, new ChessPosition(row - 1, col - 1), null));
                }
            }
        }

        return pawnMoves;
    }
}
