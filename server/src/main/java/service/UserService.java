package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.AuthData;
import model.UserData;
import request.LoginRequest;
import request.RegisterRequest;
import result.LoginResult;
import result.LogoutResult;
import result.RegisterResult;

public class UserService {

    private final UserDAO userDAO;
    private final AuthDAO authDAO;

    public UserService(UserDAO userDAO, AuthDAO authDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
    }

    public RegisterResult register(RegisterRequest registerRequest) throws DataAccessException {
        if (registerRequest.username() == null || registerRequest.password() == null || registerRequest.email() == null) {
            throw new DataAccessException(400, "Error: bad request");
        }
       if (userDAO.createUser(registerRequest.username(), registerRequest.password(), registerRequest.email()) != null) {
           AuthData newAuth = authDAO.createAuthToken(registerRequest.username());
           return new RegisterResult(newAuth.username(), newAuth.authToken());
       } else {
           throw new DataAccessException(403, "Error: already taken");
       }
    }

    public LoginResult login(LoginRequest loginRequest) throws DataAccessException {
        if (loginRequest.username() == null || loginRequest.password() == null) {
            throw new DataAccessException(400, "Error, bad request");
        }
        UserData user = userDAO.getUser(loginRequest.username());
        if (user != null) {
            if (!loginRequest.password().equals(user.password())) {
                throw new DataAccessException(401, "Error: unauthorized");
            } else {
                AuthData newAuth = authDAO.createAuthToken(loginRequest.username());
                return new LoginResult(newAuth.username(), newAuth.authToken());
            }
        } else {
            throw new DataAccessException(401, "Error, unauthorized");
        }
    }

//    public LogoutResult logout(LogoutRequest logoutRequest) throws DataAccessException {
//        if (logoutRequest.authToken() == null) {
//            throw new DataAccessException(500, "Error, bad request");
//        }
//        if (authDAO.getAuthToken(logoutRequest.authToken()) == null) {
//            throw new DataAccessException(400, "Error, bad request");
//        } else {
//            if (authDAO.deleteAuth(logoutRequest.authToken())) {
//                return new LogoutResult();
//            } else {
//                throw new DataAccessException(400, "Error, bad request");
//            }
//        }
//    }

    public LogoutResult logout(String authToken) throws DataAccessException {
        if (authToken == null) {
            throw new DataAccessException(500, "Error: no authToken found");
        }
        if (authDAO.getAuthToken(authToken) == null) {
            throw new DataAccessException(401, "Error: unauthorized");
        } else {
            if (authDAO.deleteAuth(authToken)) {
                return new LogoutResult();
            } else {
                throw new DataAccessException(500, "Error: unable to logout");
            }
        }
    }

    public void clear() throws DataAccessException {
        userDAO.clearUsers();
        authDAO.clear();
    }

}
