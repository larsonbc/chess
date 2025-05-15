package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece implements Cloneable{

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    @Override
    public ChessPiece clone() {
        try {
            ChessPiece clone = (ChessPiece) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
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
        ChessPiece piece = board.getPiece(myPosition);
        switch (piece.getPieceType()) {
            case KING:
                KingMovesCalculator kingMovesCalculator = new KingMovesCalculator();
                return kingMovesCalculator.calculateMoves(board, myPosition);
            case KNIGHT:
                KnightMovesCalculator knightMovesCalculator = new KnightMovesCalculator();
                return knightMovesCalculator.calculateMoves(board, myPosition);
            case BISHOP:
                BishopMovesCalculator bishopMovesCalculator = new BishopMovesCalculator();
                return bishopMovesCalculator.calculateMoves(board, myPosition);
            case PAWN:
                PawnMovesCalculator pawnMovesCalculator = new PawnMovesCalculator();
                return pawnMovesCalculator.calculateMoves(board, myPosition);
            case QUEEN:
                BishopMovesCalculator bishopMovesCalculator2 = new BishopMovesCalculator();
                ArrayList<ChessMove> queenMoves;
                queenMoves = bishopMovesCalculator2.calculateMoves(board, myPosition);
                RookMovesCalculator rookMovesCalculator2 = new RookMovesCalculator();
                queenMoves.addAll(rookMovesCalculator2.calculateMoves(board, myPosition));
                return queenMoves;
            case ROOK:
                RookMovesCalculator rookMovesCalculator = new RookMovesCalculator();
                return rookMovesCalculator.calculateMoves(board, myPosition);
            default:
                return List.of();
        }
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "pieceColor=" + pieceColor +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }
}
