package dataaccess;

import model.AuthData;

public interface AuthDAO {
    AuthData createAuthToken(String username) throws DataAccessException;
    String getAuthToken(String authToken) throws DataAccessException;
    AuthData getAuth(String authToken) throws DataAccessException;
    boolean deleteAuth(String authToken) throws DataAccessException;
    void clear() throws DataAccessException;
}
