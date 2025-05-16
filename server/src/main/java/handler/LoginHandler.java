package handler;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import request.LoginRequest;
import result.LoginResult;
import service.UserService;
import spark.Request;
import spark.Response;

public class LoginHandler {

    public UserService userService;

    public LoginHandler(UserService userService) {
        this.userService = userService;
    }

    public Object handleLogin(Request req, Response res) throws DataAccessException {
        var serializer = new Gson();
        LoginRequest request = serializer.fromJson(req.body(), LoginRequest.class);
        LoginResult result = userService.login(request);
        return serializer.toJson(result);
    }
}
