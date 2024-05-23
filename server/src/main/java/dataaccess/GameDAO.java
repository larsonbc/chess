package dataaccess;

import model.GameData;

import java.util.Collection;

public interface GameDAO {
    GameData createGame(String gameName);
    GameData getGame(String gameID);
    Collection<GameData> listGames();
    void updateGame(GameData game);
    void clearGames();
}
