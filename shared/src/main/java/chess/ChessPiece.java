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
                break;
            case QUEEN:
                break;
            case BISHOP:
                BishopMovesCalculator bishopMovesCalc = new BishopMovesCalculator();
                return bishopMovesCalc.pieceMoves(board, myPosition);
            case KNIGHT:
                break;
            case ROOK:
                break;
            case PAWN:
                break;
        }

        return new ArrayList<>();
//        ChessPosition testPos1 = new ChessPosition(1, 4);
//        ChessPosition testPos2 = new ChessPosition(2, 5);
//        ChessPiece testPiece = new ChessPiece(ChessGame.TeamColor.BLACK, PieceType.BISHOP);
//        ChessMove testMove = new ChessMove(testPos1, testPos2, testPiece.getPieceType());
//        ArrayList<ChessMove> testList = new ArrayList<>();
//        testList.add(testMove);
//        var listBuilder = new StringBuilder();
//        for (ChessMove move : testList)
//            listBuilder.append(move.toString());
//        //System.out.println(listBuilder);
//        return testList;
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }
}
