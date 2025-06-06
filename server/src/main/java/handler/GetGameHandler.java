package handler;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.GameData;
import service.GameService;
import spark.Request;
import spark.Response;

public class GetGameHandler {
    public GameService gameService;

    public GetGameHandler(GameService gameService) {
        this.gameService = gameService;
    }

    public Object handleGetGame(Request req, Response res) throws DataAccessException {
        var authToken = req.headers("authorization");
        String gameIDParam = req.queryParams("gameID");

        if (gameIDParam == null) {
            res.status(400);
            return "Missing gameID query parameter";
        }
        int gameID = Integer.parseInt(gameIDParam);
        GameData result = gameService.getGame(authToken, gameID);
        return new Gson().toJson(result);
    }
}
