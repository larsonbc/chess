package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.AuthData;
import request.LoginRequest;
import request.RegisterRequest;
import result.LoginResult;
import result.RegisterResult;

public class UserService {

    private final UserDAO userDAO;
    private final AuthDAO authDAO;

    public UserService(UserDAO userDAO, AuthDAO authDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
    }

    public RegisterResult register(RegisterRequest registerRequest) throws DataAccessException {
       if (userDAO.createUser(registerRequest.username(), registerRequest.password(), registerRequest.email()) != null) {
           AuthData newAuth = authDAO.createAuthToken(registerRequest.username());
           return new RegisterResult(newAuth.username(), newAuth.authToken());
       } else {
           throw new DataAccessException(403, "Error: already taken");
       }
    }

    public LoginResult login(LoginRequest loginRequest) {
        return null;
    }

    public void logout(){} //complete later

    public void clear() {
        userDAO.clearUsers();
    }

}
