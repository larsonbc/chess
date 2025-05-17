package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.AuthData;
import model.GameData;
import request.CreateGameRequest;
import request.JoinGameRequest;
import request.ListGamesRequest;
import result.CreateGameResult;
import result.JoinGameResult;
import result.ListGamesResult;

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

    public JoinGameResult joinGame(String authToken, JoinGameRequest joinRequest) throws DataAccessException {
        if (authToken == null) {
            throw new DataAccessException(400, "Error: bad request");
        }
        AuthData auth = authDAO.getAuth(authToken);
        if (auth.authToken() == null) {
            throw new DataAccessException(401, "Error: unauthorized");
        }
        if (gameDAO.getGame(joinRequest.gameID()) == null) {
            throw new DataAccessException(400, "Error: bad request");
        }
        if (gameDAO.updateGame(joinRequest.playerColor(), joinRequest.gameID(), auth.username())) {
            return new JoinGameResult();
        } else {
            throw new DataAccessException(400, "Error: bad request");
        }
    }

    public ListGamesResult listGames(String authToken, ListGamesRequest listRequest) throws DataAccessException {
        if (authToken == null) {
            throw new DataAccessException(400, "Error: bad request");
        }
        AuthData auth = authDAO.getAuth(authToken);
        if (auth.authToken() == null) {
            throw new DataAccessException(401, "Error: unauthorized");
        }
        return new ListGamesResult(gameDAO.listGames());
    }
}
