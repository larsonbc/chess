package chess;

import java.util.ArrayList;
import java.util.Collection;

public class QueenMovesCalculator implements PieceMovesCalculator{
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        ChessGame.TeamColor teamColor = board.getPiece(position).getTeamColor();
        ArrayList<ChessMove> queenMoves = new ArrayList<>();
        int currentRow = position.getRow();
        int currentCol = position.getColumn();

        //down and right
        while (currentRow > 1 && currentCol < 8) {
            currentRow--;
            currentCol++;
            if (board.getPiece(new ChessPosition(currentRow, currentCol)) != null) {
                if (board.getPiece(new ChessPosition(currentRow, currentCol)).getTeamColor() == teamColor) {
                    currentRow++;
                    currentCol--;
                    break;
                } else {
                    break;
                }
            }
        }
        while (currentRow != position.getRow()) {
            queenMoves.add(new ChessMove(position, new ChessPosition(currentRow, currentCol), null));
            currentRow++;
            currentCol--;
        }
        //up and right
        currentRow = position.getRow();
        currentCol = position.getColumn();
        while (currentRow < 8 && currentCol < 8) {
            currentRow++;
            currentCol++;
            if (board.getPiece(new ChessPosition(currentRow, currentCol)) != null) {
                if (board.getPiece(new ChessPosition(currentRow, currentCol)).getTeamColor() == teamColor) {
                    currentRow--;
                    currentCol--;
                    break;
                } else {
                    break;
                }
            }
        }
        while (currentRow != position.getRow()) {
            queenMoves.add(new ChessMove(position, new ChessPosition(currentRow, currentCol), null));
            currentRow--;
            currentCol--;
        }
        //down and left
        currentRow = position.getRow();
        currentCol = position.getColumn();
        while (currentRow > 1 && currentCol > 1) {
            currentRow--;
            currentCol--;
            if (board.getPiece(new ChessPosition(currentRow, currentCol)) != null) {
                if (board.getPiece(new ChessPosition(currentRow, currentCol)).getTeamColor() == teamColor) {
                    currentRow++;
                    currentCol++;
                    break;
                } else {
                    break;
                }
            }
        }
        while (currentRow != position.getRow()) {
            queenMoves.add(new ChessMove(position, new ChessPosition(currentRow, currentCol), null));
            currentRow++;
            currentCol++;
        }
        //up and left
        while (currentRow < 8 && currentCol > 1) {
            currentRow++;
            currentCol--;
            if (board.getPiece(new ChessPosition(currentRow, currentCol)) != null) {
                if (board.getPiece(new ChessPosition(currentRow, currentCol)).getTeamColor() == teamColor) {
                    currentRow--;
                    currentCol++;
                    break;
                } else {
                    break;
                }
            }
        }
        while (currentRow != position.getRow()) {
            queenMoves.add(new ChessMove(position, new ChessPosition(currentRow, currentCol), null));
            currentRow--;
            currentCol++;
        }

        //up
        while (currentRow < 8) {
            currentRow++;
            if (board.getPiece(new ChessPosition(currentRow, currentCol)) != null) {
                if (board.getPiece(new ChessPosition(currentRow, currentCol)).getTeamColor() == teamColor) {
                    currentRow--;
                    break;
                } else {
                    break;
                }
            }
        }
        while (currentRow != position.getRow()) {
            queenMoves.add(new ChessMove(position, new ChessPosition(currentRow, currentCol), null));
            currentRow--;
        }
        //down
        while (currentRow > 1) {
            currentRow--;
            if (board.getPiece(new ChessPosition(currentRow, currentCol)) != null) {
                if (board.getPiece(new ChessPosition(currentRow, currentCol)).getTeamColor() == teamColor) {
                    currentRow++;
                    break;
                } else {
                    break;
                }
            }
        }
        while (currentRow != position.getRow()) {
            queenMoves.add(new ChessMove(position, new ChessPosition(currentRow, currentCol), null));
            currentRow++;
        }
        //right
        while (currentCol < 8) {
            currentCol++;
            if (board.getPiece(new ChessPosition(currentRow, currentCol)) != null) {
                if (board.getPiece(new ChessPosition(currentRow, currentCol)).getTeamColor() == teamColor) {
                    currentCol--;
                    break;
                } else {
                    break;
                }
            }
        }
        while (currentCol != position.getColumn()) {
            queenMoves.add(new ChessMove(position, new ChessPosition(currentRow, currentCol), null));
            currentCol--;
        }
        //left
        while (currentCol > 1) {
            currentCol--;
            if (board.getPiece(new ChessPosition(currentRow, currentCol)) != null) {
                if (board.getPiece(new ChessPosition(currentRow, currentCol)).getTeamColor() == teamColor) {
                    currentCol++;
                    break;
                } else {
                    break;
                }
            }
        }
        while (currentCol != position.getColumn()) {
            queenMoves.add(new ChessMove(position, new ChessPosition(currentRow, currentCol), null));
            currentCol++;
        }

        return queenMoves;
    }
}
