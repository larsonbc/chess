package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import model.AuthData;
import model.UserData;
import request.LoginRequest;
import result.LoginResult;

import java.util.Objects;

public class UserService {

    //private MemoryUserDAO memoryUserDAO = new MemoryUserDAO();
    private MemoryUserDAO memoryUserDAO;
    private MemoryAuthDAO memoryAuthDAO;

    public UserService(MemoryUserDAO memoryUserDAO, MemoryAuthDAO memoryAuthDAO) {
        this.memoryUserDAO = memoryUserDAO;
        this.memoryAuthDAO = memoryAuthDAO;
    }

    public AuthData register(UserData user) throws DataAccessException {
//        if (userDAO.getUser(user.username()) == null) {
//            try {
//                userDAO.createUser(user.username(), user.password(), user.email());
//            } catch (DataAccessException e) {
//                throw new DataAccessException("username already exists"); //or should you throw DataAccessException
//            }
//        }
    //}

//        if (memoryUserDAO.getUser(user.username()) == null) {
//            try {
//                memoryUserDAO.createUser(user.username(), user.password(), user.email());
//            } catch (DataAccessException e) {
//                throw new DataAccessException("username already exists");
//            }
//        }

        if (memoryUserDAO.getUser(user.username()) == null) {
            UserData newUser = new UserData(user.username(), user.password(), user.email());
            memoryUserDAO.createUser(newUser.username(), newUser.password(), newUser.email());
            return memoryAuthDAO.createAuth(newUser);
        } else {
            throw new DataAccessException("Error: already taken"); //specs say to throw exception in data access, so this may need to be altered
            //return null;
        }

    }

    public AuthData login(UserData user) throws DataAccessException {
        UserData userLoggingIn = memoryUserDAO.getUser(user.username());
        if (userLoggingIn != null) {
            if (Objects.equals(user.password(), userLoggingIn.password())) {
                return memoryAuthDAO.createAuth(user);
            } else {
                throw new DataAccessException("Error: unauthorized");
            }
        } else throw new DataAccessException("Error: unauthorized");
    }

    public LoginResult login2(LoginRequest request) throws DataAccessException {
        UserData userLoggingIn = memoryUserDAO.getUser(request.username());
        if (userLoggingIn != null) {
            if (Objects.equals(request.password(), userLoggingIn.password())) {
                //return memoryAuthDAO.createAuth(userLoggingIn);
                AuthData newAuth = memoryAuthDAO.createAuth(userLoggingIn);
                return new LoginResult(newAuth.username(), newAuth.authToken());
            } else {
                throw new DataAccessException("Error: unauthorized");
            }
        } else throw new DataAccessException("Error: unauthorized");
    }

    public void logout(AuthData authData) {
        if (memoryAuthDAO.getAuth(authData.authToken()) != null) {
            memoryAuthDAO.deleteAuth(authData);
        }
    }

    public void clear() {
        memoryUserDAO.clearUsers();
    }

    public MemoryUserDAO getMemoryUserDAO() {
        return memoryUserDAO;
    }
}
