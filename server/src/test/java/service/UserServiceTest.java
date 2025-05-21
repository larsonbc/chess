package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.LoginRequest;
import request.RegisterRequest;
import result.LogoutResult;
import result.RegisterResult;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserService userService;

    @BeforeEach
    public void setUp() {
        userService = new UserService(new MemoryUserDAO(), new MemoryAuthDAO());
    }

    @Test
    void positiveRegister() throws DataAccessException {
        RegisterRequest testUser = new RegisterRequest("Test User", "testPassword", "testEmail@mail.com");
        String expectedName = "Test User";
        String actualName = userService.register(testUser).username();
        Assertions.assertEquals(expectedName, actualName);
    }

    @Test
    void negativeRegister() {
        RegisterRequest testUser = new RegisterRequest(null, "testPassword", "testEmail@mail.com");
        DataAccessException exception = assertThrows(DataAccessException.class, () -> userService.register(testUser));
        assertEquals("Error: bad request", exception.getMessage());
    }

    @Test
    void positiveLogin() throws DataAccessException {
        RegisterResult testUser = userService.register(new RegisterRequest("Test User", "testPassword", "testEmail@mgail.com"));
        String expectedName = "Test User";
        String actualName = userService.login(new LoginRequest(testUser.username(), "testPassword")).username();
        assertEquals(expectedName, actualName);
    }

    @Test
    void negativeLogin() throws DataAccessException {
        DataAccessException exception = assertThrows(DataAccessException.class, () -> {
            userService.login(new LoginRequest("Doesn't Exist", "password"));
        });
        assertEquals("Error: unauthorized", exception.getMessage());
    }

    @Test
    void positiveLogout() throws DataAccessException {
        RegisterResult testUser = userService.register(new RegisterRequest("Test User", "testPassword", "testEmail@mgail.com"));
        LogoutResult expected = new LogoutResult();
        LogoutResult actual = userService.logout(testUser.authToken());
        assertEquals(expected, actual);

    }

    @Test
    void negativeLogout() {
        DataAccessException exception = assertThrows(DataAccessException.class, () -> {
            userService.logout(null);
        });
        assertEquals("Error: no authToken found", exception.getMessage());
    }

    @Test
    void clear() throws DataAccessException {
        RegisterResult testUser = userService.register(new RegisterRequest("Test User", "testPassword", "testEmail@mgail.com"));
        assertDoesNotThrow(() -> {
            userService.clear();
        });
    }
}