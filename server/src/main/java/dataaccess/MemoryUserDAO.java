package dataaccess;

import model.UserData;

import java.util.ArrayList;

public class MemoryUserDAO implements UserDAO{
    private ArrayList<UserData> users;

    @Override
    public void createUser(String username, String password, String email) throws DataAccessException {
//        if (getUser(username) == null) {
//            users.add(new UserData(username, password, email));
//        } else {
//            throw new DataAccessException("username taken");
//        }
        users.add(new UserData(username, password, email));

    }

    @Override
    public UserData getUser(String userName) {
        for (UserData user : users) {
            if (user.username().equals(userName)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void clearUsers() {
        users.clear();
    }
}
