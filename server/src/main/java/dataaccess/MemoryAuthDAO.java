package dataaccess;

import model.AuthData;
import model.UserData;

import java.util.ArrayList;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO{

    private ArrayList<AuthData> auth = new ArrayList<>();

    @Override
    public AuthData createAuth(UserData u) {
        AuthData newAuth = new AuthData(UUID.randomUUID().toString(), u.username());
        auth.add(newAuth);
        return newAuth;
    }

    @Override
    public AuthData getAuth(String authToken) {
        for (AuthData item : auth) {
            if (item.authToken().equals(authToken)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public void deleteAuth(AuthData authData) {
        auth.removeIf(item -> item.equals(authData));
    }

    @Override
    public void clearAuth() {
        auth.clear();
    }
}
