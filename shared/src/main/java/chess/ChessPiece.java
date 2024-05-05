package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPiece currentPiece = board.getPiece(myPosition);
        switch (currentPiece.getPieceType()) {
            case KING:
                KingMovesCalculator kingMovesCalc = new KingMovesCalculator();
                return kingMovesCalc.pieceMoves(board, myPosition);
            case QUEEN:
                break;
            case BISHOP:
                BishopMovesCalculator bishopMovesCalc = new BishopMovesCalculator();
                return bishopMovesCalc.pieceMoves(board, myPosition);
            case KNIGHT:
                KnightMovesCalculator knightMovesCalc = new KnightMovesCalculator();
                return knightMovesCalc.pieceMoves(board, myPosition);
            case ROOK:
                RookMovesCalculator rookMovesCalc = new RookMovesCalculator();
                return rookMovesCalc.pieceMoves(board, myPosition);
            case PAWN:
                PawnMovesCalculator pawnMovesCalc = new PawnMovesCalculator();
                return pawnMovesCalc.pieceMoves(board, myPosition);
        }
        return new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "pieceColor=" + pieceColor +
                ", type=" + type +
                '}';
    }
}
