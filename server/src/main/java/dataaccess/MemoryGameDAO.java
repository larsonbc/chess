package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class MemoryGameDAO implements GameDAO{

    ArrayList<GameData> games = new ArrayList<>();
    private int nextID = 1;

    @Override
    public GameData createGame(String gameName) throws DataAccessException {
        if (gameName == null) {
            throw new DataAccessException(400, "Error: bad request");
        } else {
            GameData newGame = new GameData(nextID++, null, null, gameName, new ChessGame());
            games.add(newGame);
            return newGame;
        }
    }

    @Override
    public GameData getGame(int gameID) {
        for (GameData game : games) {
            if (game.gameID() == gameID) {
                return game;
            }
        }
        return null;
    }

    @Override
    public ArrayList<GameData> listGames() {
        return games;
    }

    @Override
    public boolean updateGame(String playerColor, int gameID, String username) throws DataAccessException {
        GameData oldGame = getGame(gameID);
        if (oldGame == null) {
            throw new DataAccessException(400, "Error: bad request");
        }
        int index = 0;
        for (int i = 0; i < games.size(); i++) {
            if (games.get(i).gameID() == oldGame.gameID()) {
                index = i;
            }
        }
        if ((oldGame.whiteUsername() != null && Objects.equals(playerColor, "WHITE")) ||
                (oldGame.blackUsername() != null && Objects.equals(playerColor, "BLACK"))) {
            throw new DataAccessException(403, "Error: already taken");
        }
        GameData updatedGame;
        if (Objects.equals(playerColor, "WHITE")) {
            updatedGame = new GameData(
                    oldGame.gameID(),
                    username,
                    oldGame.blackUsername(),
                    oldGame.gameName(),
                    oldGame.game()
            );
        } else if (Objects.equals(playerColor, "BLACK")){
            updatedGame = new GameData(
                    oldGame.gameID(),
                    oldGame.whiteUsername(),
                    username,
                    oldGame.gameName(),
                    oldGame.game()
            );
        } else {
            throw new DataAccessException(400, "Error: bad request");
        }
        games.set(index, updatedGame);
        return true;
    }

    @Override
    public void clear() throws DataAccessException {
        try {
            games.clear();
        } catch (Exception e) {
            throw new DataAccessException(500, "Error: Unable to clear");
        }
    }

    @Override
    public void saveGame(int gameID, ChessGame newGameState) throws DataAccessException {
        GameData gameData = getGame(gameID);
        if (gameData == null) {
            throw new DataAccessException(400, "Error: bad request");
        }
        int index = 0;
        for (int i = 0; i < games.size(); i++) {
            if (games.get(i).gameID() == gameData.gameID()) {
                index = i;
            }
        }
        GameData updatedGame = new GameData(
                gameData.gameID(),
                gameData.whiteUsername(),
                gameData.blackUsername(),
                gameData.gameName(),
                newGameState
        );
        games.set(index, updatedGame);
    }
}
