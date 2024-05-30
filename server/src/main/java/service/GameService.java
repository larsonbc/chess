package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import model.AuthData;
import model.GameData;
import request.JoinGameRequest;

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

    public void joinGame(JoinGameRequest joinGameRequest) throws DataAccessException {
        AuthData authData = memoryAuthDAO.getAuth(joinGameRequest.authToken());
        if (authData != null) {
            GameData game = memoryGameDAO.getGame(joinGameRequest.gameID());
            if (game != null) {
                memoryGameDAO.updateGame(joinGameRequest.gameID(), authData.username(), joinGameRequest.teamColor());
            } else throw new DataAccessException("Game doesn't exit");
        } else throw new DataAccessException("Error: unauthorized");
    }

    public void clear() {
        memoryGameDAO.clearGames();
    }

    public MemoryAuthDAO getMemoryAuthDAO() {
        return memoryAuthDAO;
    }

}
