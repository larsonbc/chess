package service;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import model.AuthData;
import model.UserData;

public class UserService {

    //private MemoryUserDAO memoryUserDAO = new MemoryUserDAO();
    private MemoryUserDAO memoryUserDAO;
    private MemoryAuthDAO memoryAuthDAO;

    public UserService(MemoryUserDAO memoryUserDAO, MemoryAuthDAO memoryAuthDAO) {
        this.memoryUserDAO = memoryUserDAO;
        this.memoryAuthDAO = memoryAuthDAO;
    }

    public AuthData register(UserData user) {
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
            //throw new DataAccessException("username already exists"); //specs say to throw exception in data access, so this may need to be altered
            return null;
        }

    }

    public AuthData login(UserData user) {
        if (memoryUserDAO.getUser(user.username()) != null) {
            return memoryAuthDAO.createAuth(user);
        } else return null; //may need to be edited
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
