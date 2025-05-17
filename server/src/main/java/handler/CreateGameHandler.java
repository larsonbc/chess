package handler;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import request.CreateGameRequest;
import result.CreateGameResult;
import service.GameService;
import spark.Request;
import spark.Response;

public class CreateGameHandler {
    public GameService gameService;

    public CreateGameHandler(GameService gameService) {this.gameService = gameService;}

    public Object handleCreateGame(Request req, Response res) throws DataAccessException {//may want to try generics: Class<T> responseClass
        var serializer = new Gson();
        var authToken = req.headers("authorization");
        CreateGameRequest request = serializer.fromJson(req.body(), CreateGameRequest.class);
        CreateGameResult result = gameService.createGame(authToken, request);
        return serializer.toJson(result);
    }
}
