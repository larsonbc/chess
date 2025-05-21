package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.CreateGameRequest;
import request.RegisterRequest;
import result.CreateGameResult;
import result.RegisterResult;

import static org.junit.jupiter.api.Assertions.*;
class GameServiceTest {

    private  GameService gameService;
    private UserService userService;
    private RegisterResult testUser;

    @BeforeEach
    public void setUp() throws DataAccessException {
//        gameService = new GameService(new MemoryAuthDAO(), new MemoryGameDAO());
//        userService = new UserService(new MemoryUserDAO(), new MemoryAuthDAO());
//        testUser = userService.register(new RegisterRequest("Test User", "testPassword", "testEmail@mail.com"));
    }

    @Test
    void createGamePositive() throws DataAccessException {
//        CreateGameResult result = gameService.createGame(testUser.authToken(), new CreateGameRequest("Test Game"));
//        int expectedID = 1;
//        int actualID = result.gameID();
//        assertEquals(expectedID, actualID);
    }

    @Test
    void createGameNegative() {
    }

    @Test
    void joinGamePositive() {
    }

    @Test
    void joinGameNegative() {
    }

    @Test
    void listGamesPositive() {
    }

    @Test
    void listGamesNegative() {
    }

    @Test
    void clear() {
    }
}