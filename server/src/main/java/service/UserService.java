package service;

import dataaccess.UserDAO;
import request.LoginRequest;
import request.RegisterRequest;
import result.LoginResult;
import result.RegisterResult;

public class UserService {

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public RegisterResult register(RegisterRequest registerRequest) {
        return null;
    }

    public LoginResult login(LoginRequest loginRequest) {
        return null;
    }

    public void logout(){} //complete later

}
