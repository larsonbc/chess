package dataaccess;

import model.UserData;

import java.util.ArrayList;
import java.util.Objects;

public class MemoryUserDAO implements UserDAO{
    private ArrayList<UserData> users = new ArrayList<>();

//    public MemoryUserDAO(ArrayList<UserData> users) {
//        this.users = users;
//    }

    @Override
    public void createUser(String username, String password, String email) throws DataAccessException { //add throws DataAccessException
        if (getUser(username) == null) {
            users.add(new UserData(username, password, email));
        } else {
            throw new DataAccessException("Username taken");
        }
        //users.add(new UserData(username, password, email));

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

//    public boolean authenticatePassword(String password) {
//        for (UserData user : users) {
//            return user.password().equals(password);
//        }
//        return false;
//    }

    @Override
    public void clearUsers() {
        users.clear();
    }

    public ArrayList<UserData> getUsers() {
        return users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemoryUserDAO that = (MemoryUserDAO) o;
        return Objects.equals(users, that.users);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(users);
    }
}
