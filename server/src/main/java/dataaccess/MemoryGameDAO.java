package dataaccess;

import model.GameData;

import java.util.Collection;
import java.util.List;

public class MemoryGameDAO implements GameDAO{
    @Override
    public GameData createGame(String gameName) {
        return null;
    }

    @Override
    public GameData getGame(String gameID) {
        return null;
    }

    @Override
    public Collection<GameData> listGames() {
        return List.of();
    }

    @Override
    public void updateGame(GameData game) {

    }

    @Override
    public void clearGames() {

    }
}
