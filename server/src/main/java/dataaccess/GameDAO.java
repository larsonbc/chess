package dataaccess;

import model.GameData;

import java.util.ArrayList;

public interface GameDAO {
    GameData createGame(String gameName) throws DataAccessException;
    GameData getGame(int gameID);
    ArrayList<GameData> listGames();
    boolean updateGame(String playerColor, int gameID);
}
