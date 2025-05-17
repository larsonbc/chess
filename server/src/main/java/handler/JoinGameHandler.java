package handler;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import request.JoinGameRequest;
import result.JoinGameResult;
import service.GameService;
import spark.Request;
import spark.Response;

public class JoinGameHandler {
    public GameService gameService;

    public JoinGameHandler(GameService gameService) {this.gameService = gameService;}

    public Object handleJoinGame(Request req, Response res) throws DataAccessException {//may want to try generics: Class<T> responseClass
        var serializer = new Gson();
        var authToken = req.headers("authorization");
        JoinGameRequest request = serializer.fromJson(req.body(), JoinGameRequest.class);
        JoinGameResult result = gameService.joinGame(authToken, request);
        return serializer.toJson(result);
    }
}
