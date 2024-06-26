package dataaccess;

import model.UserData;

public interface UserDAO {
    void createUser(String username, String password, String email) throws DataAccessException;
    UserData getUser(String userName);
    void clearUsers();
}
