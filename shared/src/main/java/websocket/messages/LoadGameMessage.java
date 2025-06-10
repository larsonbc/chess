package websocket.messages;

import chess.ChessGame;
import chess.ChessMove;

public class LoadGameMessage extends ServerMessage{
    ChessGame game;
    private ChessMove lastMove;

    public LoadGameMessage(ServerMessageType type) {
        super(type);
    }

    public ChessGame getGame() {
        return game;
    }

    public void setGame(ChessGame game) {
        this.game = game;
    }

    public ChessMove getLastMove() {
        return lastMove;
    }

    public void setLastMove(ChessMove lastMove) {
        this.lastMove = lastMove;
    }
}
