package dataaccess;

import model.UserData;

public class MemoryUserDAO implements UserDAO{
    @Override
    public void createUser(String username, String password, String email) {

    }

    @Override
    public UserData getUser(String username) {
        return null;
    }

    @Override
    public void clearUsers() {

    }
}
