package websocket.commands;

import chess.ChessMove;

public class MakeMoveCommand extends UserGameCommand{
    ChessMove move;

    public MakeMoveCommand(CommandType commandType, String authToken, Integer gameID) {
        super(commandType, authToken, gameID);
    }

    public ChessMove getMove() {
        return move;
    }

    public void setMove(ChessMove move) {
        this.move = move;
    }
}
