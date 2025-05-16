package handler;

import com.google.gson.Gson;
import result.ClearResult;
import service.UserService;
import spark.Request;
import spark.Response;

public class ClearDBHandler {
    public UserService userService;

    public ClearDBHandler(UserService userService) {
        this.userService = userService;
    }

    public Object handleRequest(Request req, Response res) {
        userService.clear();
        var serializer = new Gson();
        return serializer.toJson(new ClearResult());
    }
}
