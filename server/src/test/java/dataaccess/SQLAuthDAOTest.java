package dataaccess;

import model.AuthData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SQLAuthDAOTest {

    private SQLAuthDAO authDAO;

    @BeforeEach
    public void setUp() throws DataAccessException {
        authDAO = new SQLAuthDAO();
        authDAO.clear();
    }

    @Test
    void createAuthTokenPositive() {
        assertDoesNotThrow(() -> {
            authDAO.createAuthToken("Test Username");
        });
    }

    @Test
    void createAuthTokenNegative() {
    }

    @Test
    void getAuthTokenPositive() throws DataAccessException {
        AuthData newAuth = authDAO.createAuthToken("Test Username");
        String actual = authDAO.getAuthToken(newAuth.authToken());
        assertEquals(newAuth.authToken(), actual);
    }

    @Test
    void getAuthTokenNegative() throws DataAccessException {
    }

    @Test
    void getAuthPositive() throws DataAccessException {
        AuthData newAuth = authDAO.createAuthToken("Test Username");
        String actual = authDAO.getAuthToken(newAuth.authToken());
        assertEquals(newAuth.authToken(), actual);
    }

    @Test
    void getAuthNegative() throws DataAccessException {
    }

    @Test
    void deleteAuthPositive() throws DataAccessException {
        AuthData newAuth = authDAO.createAuthToken("Test Username");
        assertDoesNotThrow(() -> {
            authDAO.deleteAuth(newAuth.authToken());
        });
    }

    @Test
    void deleteAuthNegative() throws DataAccessException {
    }

    @Test
    void clear() throws DataAccessException {
        AuthData newAuth = authDAO.createAuthToken("Test Username");
        assertDoesNotThrow(() -> {
            authDAO.clear();
        });
    }
}