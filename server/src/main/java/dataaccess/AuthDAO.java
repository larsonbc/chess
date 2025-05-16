package dataaccess;

import model.AuthData;

public interface AuthDAO {
    AuthData createAuthToken(String username);
}
