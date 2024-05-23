package dataaccess;

import model.AuthData;
import model.UserData;

public interface AuthDAO {
    AuthData createAuth(UserData u);
    AuthData getAuth(String authToken);
    void deleteAuth(AuthData authData);
    void clearAuth();
}
