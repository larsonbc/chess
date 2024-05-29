package dataaccess;

import chess.ChessGame;
import model.AuthData;
import model.GameData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class MemoryGameDAO implements GameDAO{

    private ArrayList<GameData> games = new ArrayList<>();
    private int id = 0;

    @Override
    public GameData createGame(String gameName) {
        id++;
        GameData newGame = new GameData(id, null, null, gameName, new ChessGame());
        games.add(newGame);
        return newGame;
    }

    @Override
    public GameData getGame(int gameID) {
        for (GameData item : games) {
            if (item.gameID() == gameID) {
                return item;
            }
        }
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
