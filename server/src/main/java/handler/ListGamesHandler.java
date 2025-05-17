package handler;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import request.JoinGameRequest;
import request.ListGamesRequest;
import result.JoinGameResult;
import result.ListGamesResult;
import service.GameService;
import spark.Request;
import spark.Response;

public class ListGamesHandler {
    public GameService gameService;

    public ListGamesHandler(GameService gameService) {this.gameService = gameService;}

    public Object handleListGames(Request req, Response res) throws DataAccessException {//may want to try generics: Class<T> responseClass
        var serializer = new Gson();
        var authToken = req.headers("authorization");
        ListGamesRequest request = serializer.fromJson(req.body(), ListGamesRequest.class);
        ListGamesResult result = gameService.listGames(authToken, request);
        return serializer.toJson(result);
    }
}
