package dataaccess;

import model.AuthData;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO{

    private ArrayList<AuthData> authentications = new ArrayList<>();

    @Override
    public AuthData createAuthToken(String username) {
        AuthData newAuth = new AuthData(UUID.randomUUID().toString(), username);
        authentications.add(newAuth);
        return newAuth;
    }

    @Override
    public String getAuthToken(String authToken) {
        for (AuthData auth : authentications) {
            if (Objects.equals(auth.authToken(), authToken)) {
                return auth.authToken();
            }
        }
        return null;
    }

    @Override
    public AuthData getAuth(String authToken) {
        for (AuthData auth : authentications) {
            if (Objects.equals(auth.authToken(), authToken)) {
                return auth;
            }
        }
        return null;
    }

    @Override
    public boolean deleteAuth(String authToken) {
        for (AuthData auth : authentications) {
            if (Objects.equals(auth.authToken(), authToken)) {
                authentications.remove(auth);
                return true;
            }
        }
        return false;
    }

    @Override
    public void clear() throws DataAccessException {
        try {
            authentications.clear();
        } catch (Exception e) {
            throw new DataAccessException(500, "Error: Unable to clear");
        }
    }


}
