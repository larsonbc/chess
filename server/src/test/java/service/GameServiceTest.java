package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {

    private GameService gameService;
    public AuthData testAuth;

    @BeforeEach
    void setUp() {
        gameService = new GameService(new MemoryGameDAO(), new MemoryAuthDAO());
        testAuth = gameService.getMemoryAuthDAO().createAuth(new UserData("TestUser", "testPassword", "testEmail@email.com"));
    }

    @Test
    void testCreateGame() throws DataAccessException {
        int expected = 1;
        int actual = gameService.createGame(testAuth.authToken(), "First Game");
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void testCreatingMultipleGames() throws DataAccessException {
        int expected = 1;
        int actual = gameService.createGame(testAuth.authToken(), "First Game");
        Assertions.assertEquals(actual, expected);
        expected++;
        actual = gameService.createGame(testAuth.authToken(), "Second Game");
        Assertions.assertEquals(actual, expected);
        expected++;
        actual = gameService.createGame(testAuth.authToken(), "Third Game");
        Assertions.assertEquals(actual, expected);

    }

    @Test
    void listGames() {
    }

    @Test
    void joinGame() {
    }

    @Test
    void deleteAllGames() {
    }
}