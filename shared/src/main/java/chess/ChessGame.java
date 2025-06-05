package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private ChessBoard board = new ChessBoard();
    private TeamColor teamTurn = TeamColor.WHITE;
    private ChessGame.TeamColor resignedPlayer = null;

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
        if (board.getPiece(startPosition) != null) {
            ChessPiece piece = board.getPiece(startPosition);
            Collection<ChessMove> moves1 = piece.pieceMoves(board, startPosition);
            ArrayList<ChessMove> moves2 = new ArrayList<>();
            ChessBoard originalBoard = board.clone();

            for (ChessMove move : moves1) {
                ChessBoard boardClone = board.clone();
                setBoard(boardClone);
                board.addPiece(move.getEndPosition(), piece);
                board.addPiece(startPosition, null);
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
        if (validMoves(move.getStartPosition()) == null || !validMoves(move.getStartPosition()).contains(move)
                || board.getPiece(new ChessPosition(move.getStartPosition().getRow(),
                move.getStartPosition().getColumn())).getTeamColor() != getTeamTurn()) {
            throw new InvalidMoveException();
        } else {
            ChessPiece piece = board.getPiece(move.getStartPosition());
            board.addPiece(move.getEndPosition(), piece);
            board.addPiece(move.getStartPosition(), null);
            if (piece.getTeamColor() == TeamColor.WHITE && piece.getPieceType() == ChessPiece.PieceType.PAWN && move.getEndPosition().getRow() == 8) {
                board.addPiece(move.getEndPosition(), new ChessPiece(piece.getTeamColor(),move.getPromotionPiece() ));
            }
            if (piece.getTeamColor() == TeamColor.BLACK && piece.getPieceType() == ChessPiece.PieceType.PAWN && move.getEndPosition().getRow() == 1) {
                board.addPiece(move.getEndPosition(), new ChessPiece(piece.getTeamColor(),move.getPromotionPiece() ));
            }
            //getTeamTurn() == TeamColor.WHITE ? setTeamTurn(TeamColor.BLACK) : setTeamTurn(TeamColor.WHITE);
            if (getTeamTurn() == TeamColor.WHITE) {
                setTeamTurn(TeamColor.BLACK);
            } else {
                setTeamTurn(TeamColor.WHITE);
            }
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                ChessPosition position = new ChessPosition(i, j);
                ChessPiece piece = board.getPiece(position);

                if (piece == null || piece.getTeamColor() == teamColor || piece.getTeamColor() == null) {
                    continue;
                }

                Collection<ChessMove> moves = piece.pieceMoves(board, position);
                for (ChessMove move : moves) {
                    ChessPiece destinationPiece = board.getPiece(move.getEndPosition());
                    if (destinationPiece != null && destinationPiece.getPieceType() == ChessPiece.PieceType.KING) {
                        return true;
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
        }
        ArrayList<ChessMove> moves = new ArrayList<>();
        for (int k = 1; k < 9; k++) {
            for (int l = 1; l < 9; l++) {
                if (board.getPiece(new ChessPosition(k, l)) != null && board.getPiece(new ChessPosition(k, l)).getTeamColor() == teamColor) {
                    if (validMoves(new ChessPosition(k, l)) != null) {
                        moves.addAll(validMoves(new ChessPosition(k, l)));
                    }
                }
            }
        }
        return moves.isEmpty();
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
        return moves.isEmpty() && !isInCheck(teamColor);
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

    public void resign(ChessGame.TeamColor playerColor) {
        this.resignedPlayer = playerColor;
    }

    public boolean isResigned() {
        return resignedPlayer != null;
    }

    public ChessGame.TeamColor getResignedPlayer() {
        return resignedPlayer;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return Objects.equals(board, chessGame.board) && teamTurn == chessGame.teamTurn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, teamTurn);
    }
}
