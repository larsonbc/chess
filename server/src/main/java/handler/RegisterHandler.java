package handler;

import com.google.gson.Gson;
import request.RegisterRequest;
import result.RegisterResult;
import service.UserService;
import spark.Request;
import spark.Response;

public class RegisterHandler {

    public UserService userService;

    public RegisterHandler(UserService userService) {
        this.userService = userService;
    }

    public Object handleRequest(Request req, Response res) {//may want to try generics: Class<T> responseClass
        var serializer = new Gson();
        RegisterRequest request = serializer.fromJson(req.body(), RegisterRequest.class);
        RegisterResult result = userService.register(request);
        return serializer.toJson(result);
    }
}
