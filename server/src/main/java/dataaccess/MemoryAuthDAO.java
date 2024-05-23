package dataaccess;

import model.AuthData;
import model.UserData;

public class MemoryAuthDAO implements AuthDAO{
    @Override
    public AuthData createAuth(UserData u) {
        return null;
    }

    @Override
    public AuthData getAuth(String authToken) {
        return null;
    }

    @Override
    public void deleteAuth(AuthData authData) {

    }

    @Override
    public void clearAuth() {

    }
}
