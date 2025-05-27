package dataaccess;

import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SQLUserDAOTest {

    private SQLUserDAO userDAO;

    @BeforeEach
    public void setUp() throws DataAccessException {
        userDAO = new SQLUserDAO();
        userDAO.clearUsers();
    }

    @Test
    void createUserPositive() throws DataAccessException {
        UserData newUser = userDAO.createUser("TestName", "TestPassword", "testEmail@mail.com");
        assertEquals("TestName", newUser.username());
    }

    @Test
    void createUserNegative() throws DataAccessException {
        //UserData newUser = userDAO.createUser("TestName", "TestPassword", "testEmail@mail.com");
//        DataAccessException exception = assertThrows(DataAccessException.class, () ->
//                userDAO.createUser(null, "password", "email"));
//        assertEquals("Error: something", exception.getMessage());
    }

    @Test
    void getUserPositive() throws DataAccessException {
        UserData newUser = userDAO.createUser("TestName", "TestPassword", "testEmail@mail.com");
        UserData result = userDAO.getUser("TestName");
        assertEquals(newUser.username(), result.username());
    }

    @Test
    void getUserNegative() throws DataAccessException {
    }

    @Test
    void clearUsers() throws DataAccessException {
        UserData newUser = userDAO.createUser("TestName", "TestPassword", "testEmail@mail.com");
        assertDoesNotThrow(() -> {
            userDAO.clearUsers();
        });
    }
}