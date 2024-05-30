package service;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import model.GameData;

import java.util.ArrayList;

public class GameService {

    private MemoryGameDAO memoryGameDAO;
    private MemoryAuthDAO memoryAuthDAO;

    public GameService(MemoryGameDAO memoryGameDAO, MemoryAuthDAO memoryAuthDAO) {
        this.memoryGameDAO = memoryGameDAO;
        this.memoryAuthDAO = memoryAuthDAO;
    }

    public int createGame(String authToken, String gameName) throws DataAccessException {
        if (memoryAuthDAO.getAuth(authToken) != null) {
            return memoryGameDAO.createGame(gameName).gameID();
        } else throw new DataAccessException("Error: unauthorized");
    }

    public ArrayList<GameData> listGames(String authToken) throws DataAccessException {
        if (memoryAuthDAO.getAuth(authToken) != null) {
            return (ArrayList<GameData>) memoryGameDAO.listGames();
        } else throw new DataAccessException("Error: unauthorized");
    }

    public void joinGame(ChessGame.TeamColor playerColor, int gameID) {

    }

    public void deleteAllGames() {

    }

    public MemoryAuthDAO getMemoryAuthDAO() {
        return memoryAuthDAO;
    }

}
