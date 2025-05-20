package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.AuthData;
import model.GameData;
import model.GameSummary;
import request.CreateGameRequest;
import request.JoinGameRequest;
import request.ListGamesRequest;
import result.CreateGameResult;
import result.JoinGameResult;
import result.ListGamesResult;

import java.util.ArrayList;
import java.util.stream.Collectors;

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
        ArrayList<GameData> fullGames = (gameDAO.listGames());
        ArrayList<GameSummary> summaries = new ArrayList<>();
        for (GameData game : fullGames) {
            GameSummary newSummary = new GameSummary(game.gameID(), game.whiteUsername(), game.blackUsername(), game.gameName());
            summaries.add(newSummary);
        }
        return new ListGamesResult(summaries);
//        ArrayList<GameSummary> summaries = (ArrayList<GameSummary>) fullGames.stream().map(game -> new GameSummary(game.gameID(), game.whiteUsername(), game.blackUsername(), game.gameName()))
//                .toList();
//        return new ListGamesResult(summaries);
        //return new ListGamesResult(gameDAO.listGames());
    }
}
