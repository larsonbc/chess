package dataaccess;

import model.UserData;

import java.util.ArrayList;

public class MemoryUserDAO implements UserDAO{

    ArrayList<UserData> users = new ArrayList<>();

    @Override
    public UserData createUser(String username, String password, String email) throws DataAccessException {
        if (getUser(username) == null) {
            UserData newUser = new UserData(username, password, email);
            users.add(newUser);
            return newUser;
        } else {
            throw new DataAccessException(403, "Error: already taken");
        }
    }

    @Override
    public UserData getUser(String username) {
        for (UserData user : users) {
            if (user.username().equals(username)) {
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
