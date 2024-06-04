package handler;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import request.LoginRequest;
import request.RegisterRequest;
import result.LoginResult;
import service.UserService;

public class RegisterHandler {

    UserService userService;

    public RegisterHandler(UserService uService) {
        userService = uService;
    }

    public <T> T handleRequest(Object req, Class<T> responseClass) throws DataAccessException {
//        var gson = new Gson();
//        RegisterRequest request = gson.fromJson((String) req, RegisterRequest.class);
//
//        LoginResult result = userService.register2(request);
//        return gson.toJson(result);
        return null;
    }
}
