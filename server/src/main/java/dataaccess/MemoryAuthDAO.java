package dataaccess;

import model.AuthData;

import java.util.ArrayList;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO{

    private ArrayList<AuthData> authentications = new ArrayList<>();

    @Override
    public AuthData createAuthToken(String username) {
        AuthData newAuth = new AuthData(UUID.randomUUID().toString(), username);
        authentications.add(newAuth);
        return newAuth;
    }
}
