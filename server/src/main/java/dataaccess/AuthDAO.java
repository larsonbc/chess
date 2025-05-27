package dataaccess;

import model.AuthData;

public interface AuthDAO {
    AuthData createAuthToken(String username) throws DataAccessException;
    String getAuthToken(String authToken);
    AuthData getAuth(String authToken);
    boolean deleteAuth(String authToken) throws DataAccessException;
    void clear() throws DataAccessException;
}
