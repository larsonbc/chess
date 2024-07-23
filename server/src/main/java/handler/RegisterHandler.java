package handler;

import dataaccess.DataAccessException;
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
