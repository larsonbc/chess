package dataaccess;

import model.AuthData;

public interface AuthDAO {
    AuthData createAuthToken(String username);
    String getAuthToken(String authToken);
    AuthData getAuth(String authToken);
    boolean deleteAuth(String authToken);
}
