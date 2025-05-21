package handler;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import result.ClearResult;
import service.GameService;
import service.UserService;
import spark.Request;
import spark.Response;

public class ClearDBHandler {
    public UserService userService;
    public GameService gameService;

    public ClearDBHandler(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    public Object handleRequest(Request req, Response res) throws DataAccessException {
        userService.clear();
        gameService.clear();
        var serializer = new Gson();
        return serializer.toJson(new ClearResult());
    }
}
