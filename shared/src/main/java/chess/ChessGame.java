package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private ChessBoard board = new ChessBoard();
    private TeamColor teamTurn = TeamColor.WHITE;

    public ChessGame() {
        board.resetBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
//        ChessPiece piece = board.getPiece(startPosition);
//        Collection<ChessMove> moves1 = piece.pieceMoves(board, startPosition);
//        ArrayList<ChessMove> moves2 = new ArrayList<>();
//        ChessBoard originalBoard = (ChessBoard) board.clone();
//
//        //System.out.println(moves1);
//
//        for (ChessMove move : moves1) {
//            //System.out.println("new move: " + move);
//            ChessBoard boardClone = (ChessBoard) board.clone();
//            setBoard(boardClone);
//            board.addPiece(move.getEndPosition(), piece);
//            //board.addPiece(move.getStartPosition(), new ChessPiece(null, null));
//            board.addPiece(startPosition, null);
//            //System.out.println(board.getPiece(new ChessPosition(startPosition.getRow(), startPosition.getColumn())));
//            if (!isInCheck(piece.getTeamColor())) {
//                moves2.add(move);
//            }
//            setBoard(originalBoard);
//        }
//        setBoard(originalBoard);
//
//        if (moves2.isEmpty()) {
//            return null;
//        } else {
//            return moves2;
//        }

        if (board.getPiece(startPosition) != null) {
            ChessPiece piece = board.getPiece(startPosition);
            Collection<ChessMove> moves1 = piece.pieceMoves(board, startPosition);
            ArrayList<ChessMove> moves2 = new ArrayList<>();
            ChessBoard originalBoard = (ChessBoard) board.clone();

            //System.out.println(moves1);

            for (ChessMove move : moves1) {
                //System.out.println("new move: " + move);
                ChessBoard boardClone = (ChessBoard) board.clone();
                setBoard(boardClone);
                board.addPiece(move.getEndPosition(), piece);
                //board.addPiece(move.getStartPosition(), new ChessPiece(null, null));
                board.addPiece(startPosition, null);
                //System.out.println(board.getPiece(new ChessPosition(startPosition.getRow(), startPosition.getColumn())));
                if (!isInCheck(piece.getTeamColor())) {
                    moves2.add(move);
                }
                setBoard(originalBoard);
            }
            setBoard(originalBoard);

            return moves2;
        } else {
            return null;
        }
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        if (validMoves(move.getStartPosition()) == null || !validMoves(move.getStartPosition()).contains(move)) {
            throw new InvalidMoveException();
        } else {
            //System.out.println("not supposed to be here");
            ChessPiece piece = board.getPiece(move.getStartPosition());
            board.addPiece(move.getEndPosition(), piece);
//        board.addPiece(move.getStartPosition(), new ChessPiece(null, null));
            board.addPiece(move.getStartPosition(), null);
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        //System.out.println(board.getPiece(new ChessPosition(4,6)));
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
//                if (board.getPiece(new ChessPosition(i,j)) != null && board.getPiece(new ChessPosition(i,j)).getTeamColor() != teamColor) {
                if (board.getPiece(new ChessPosition(i,j)) != null && board.getPiece(new ChessPosition(i,j)).getTeamColor() != teamColor && board.getPiece(new ChessPosition(i,j)).getTeamColor() != null) {

                    //System.out.println(board.getPiece(new ChessPosition(i,j)));
//                    System.out.println(i + "," + j);
//                    System.out.println(board.getPiece(new ChessPosition(i,j)));
                    Collection<ChessMove> moves = board.getPiece(new ChessPosition(i,j)).pieceMoves(board, new ChessPosition(i, j));
                    //System.out.println("here");
                    for (ChessMove move : moves) {
                        if (board.getPiece(move.getEndPosition()) != null && board.getPiece(move.getEndPosition()).getPieceType() == ChessPiece.PieceType.KING) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        if (!isInCheck(teamColor)) {
            return false;
        } else {
            ArrayList<ChessMove> moves = new ArrayList<>();
            for (int i = 1; i < 9; i++) {
                for (int j = 1; j < 9; j++) {
                    if (board.getPiece(new ChessPosition(i, j)) != null && board.getPiece(new ChessPosition(i, j)).getTeamColor() == teamColor) {
                        if (validMoves(new ChessPosition(i, j)) != null) {
                            moves.addAll(validMoves(new ChessPosition(i, j)));
                        }
                    }
                }
            }
            return moves.isEmpty();
        }
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                if (board.getPiece(new ChessPosition(i,j)) != null && board.getPiece(new ChessPosition(i,j)).getTeamColor() == teamColor) {
                    if (validMoves(new ChessPosition(i, j)) != null) {
                        moves.addAll(validMoves(new ChessPosition(i, j)));
                    }
                }
            }
        }
        return moves.isEmpty();
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }
}
