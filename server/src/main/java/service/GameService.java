package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.GameData;
import request.CreateGameRequest;
import result.CreateGameResult;

public class GameService {

    private final AuthDAO authDAO;
    private final GameDAO gameDAO;

    public GameService(AuthDAO authDAO, GameDAO gameDAO) {
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }

    public CreateGameResult createGame(String authToken, CreateGameRequest createRequest) throws DataAccessException {
        if (authToken == null) {
            throw new DataAccessException(400, "Error: bad request");
        }
        if (authDAO.getAuthToken(authToken) == null) {
            throw new DataAccessException(401, "Error: unauthorized");
        }
        if (createRequest.gameName() == null) {
            throw new DataAccessException(400, "Error: bad request");
        } else {
            GameData newGame = gameDAO.createGame(createRequest.gameName());
            return new CreateGameResult(newGame.gameID());
        }
    }
}
