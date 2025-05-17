package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;

public class MemoryGameDAO implements GameDAO{

    ArrayList<GameData> games = new ArrayList<>();
    private int nextID = 1;

    @Override
    public GameData createGame(String gameName) throws DataAccessException {
        if (gameName == null) {
            throw new DataAccessException(400, "Error: bad request");
        } else {
            GameData newGame = new GameData(nextID++, "whiteUsername", "blackUsername", gameName, new ChessGame());
            games.add(newGame);
            return newGame;
        }
    }

    @Override
    public GameData getGame(int gameID) {
        return null;
    }

    @Override
    public ArrayList<GameData> listGames() {
        return null;
    }

    @Override
    public boolean updateGame(String playerColor, int gameID) {
        return false;
    }
}
