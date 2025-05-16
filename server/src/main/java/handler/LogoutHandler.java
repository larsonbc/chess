package handler;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import result.LogoutResult;
import service.UserService;
import spark.Request;
import spark.Response;

public class LogoutHandler {
    public UserService userService;

    public LogoutHandler(UserService userService) {
        this.userService = userService;
    }

    public Object handleLogout(Request req, Response res) throws DataAccessException {
        var serializer = new Gson();
        var authToken = req.headers("authorization");
        LogoutResult result = userService.logout(authToken);
        return serializer.toJson(result);
    }
}
