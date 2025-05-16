package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.RegisterRequest;

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
//        RegisterRequest testUser = new RegisterRequest("Test User", "testPassword", "testEmail@mail.com");
//        String expectedName = "Test User";
//        String actualName = userService.register(testUser).username();
//        Assertions.assertEquals(expectedName, actualName);
    }

    @Test
    void login() {
    }

    @Test
    void logout() {
    }

    @Test
    void clear() {
    }
}