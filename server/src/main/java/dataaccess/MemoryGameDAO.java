package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;
import java.util.Collection;

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
        return games;
    }

    @Override
    public void updateGame(int gameID, String userName, ChessGame.TeamColor teamColor) {
        GameData gameToUpdate = getGame(gameID);
        if (gameToUpdate != null) {
            for (GameData item : games) {
                if (item.gameID() == gameID) {
                    games.remove(item);
                    if (teamColor.equals(ChessGame.TeamColor.WHITE)) {
                        GameData newGame = new GameData(gameID, userName, item.blackUsername(), item.gameName(), item.game());
                        games.add(newGame);
                    } else games.add(new GameData(gameID, item.whiteUsername(), userName, item.gameName(), item.game()));
                }
            }
        }
    }

    @Override
    public void clearGames() {
        games.clear();
    }
}
