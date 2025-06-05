package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.sql.SQLException;
import java.util.ArrayList;

public interface GameDAO {
    GameData createGame(String gameName) throws DataAccessException;
    GameData getGame(int gameID);
    ArrayList<GameData> listGames();
    boolean updateGame(String playerColor, int gameID, String username) throws DataAccessException;
    void clear() throws DataAccessException;
    void saveGame(int gameID, ChessGame newGameState) throws DataAccessException;
}
