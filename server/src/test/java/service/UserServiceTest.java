package service;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import model.UserData;
import org.eclipse.jetty.server.Authentication;
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
    public void register() {
        UserData testUser = new UserData("Test User", "testPassword", "testEmail@email.com");
        String expectedName = "Test User";
        String actualName = userService.register(testUser).username();
        Assertions.assertEquals(expectedName, actualName);

    }

    @Test
    public void login() {
    }

    @Test
    public void logout() {
    }

    @Test
    public void clear() {
        UserData testUser = new UserData("Test User", "testPassword", "testEmail@email.com");
        userService.register(testUser);
        ArrayList<UserData> expected = new ArrayList<>();
        userService.clear();
        ArrayList<UserData> actual = userService.getMemoryUserDAO().getUsers();
        Assertions.assertEquals(expected,actual);

    }
}