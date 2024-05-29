package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserService userService;
    private ArrayList<UserData> expected;

    @BeforeEach
    public void setUp() {
        userService = new UserService(new MemoryUserDAO(), new MemoryAuthDAO());
        expected = new ArrayList<>();
    }

    @Test
    public void positiveRegisterTest() throws DataAccessException {
        UserData testUser = new UserData("Test User", "testPassword", "testEmail@email.com");
        String expectedName = "Test User";
        String actualName = userService.register(testUser).username();
        Assertions.assertEquals(expectedName, actualName);

    }

    @Test
    public void negativeRegisterTest() throws DataAccessException {
        UserData testUser = new UserData("Test User", "testPassword", "testEmail@email.com");
        UserData duplicateUser = new UserData("Test User", "testPassword", "testEmail@email.com");
        String actualName = userService.register(testUser).username();
        Exception exception = assertThrows(DataAccessException.class, () -> { userService.register(duplicateUser); });
        String expectedMessage = "Error: already taken";
        String actualMessage = exception.getMessage();
        System.out.println(actualMessage);
        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    public void positiveLoginTest() throws DataAccessException {
        UserData testUser = new UserData("Test User", "testPassword", "testEmail@email.com");
        String firstActualAuthToken = userService.register(testUser).authToken();
        String secondActualAuthToken = userService.login(testUser).authToken();
        Assertions.assertNotEquals(firstActualAuthToken, secondActualAuthToken);
    }

    @Test
    public void testUnregisteredUserLogin() throws DataAccessException {
        UserData testUser = new UserData("Test User", "testPassword", "testEmail@email.com");
        String actualAuthToken = userService.register(testUser).authToken();
        Exception exception = assertThrows(DataAccessException.class, () -> userService.login(new UserData("Does Not Exist", "no", "testEmail@email.com")));
        String expectedMessage = "Error: unauthorized";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testSuccessfulLogout() throws DataAccessException {
        UserData testUser = new UserData("Test User", "testPassword", "testEmail@email.com");
        AuthData authToDelete = userService.register(testUser);
        userService.logout(authToDelete);
    }

    @Test
    public void testClear() throws DataAccessException {
        UserData testUser = new UserData("Test User", "testPassword", "testEmail@email.com");
        userService.register(testUser);
        ArrayList<UserData> expected = new ArrayList<>();
        userService.clear();
        ArrayList<UserData> actual = userService.getMemoryUserDAO().getUsers();
        Assertions.assertEquals(expected,actual);

    }
}