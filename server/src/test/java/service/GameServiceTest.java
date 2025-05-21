package service;

import dataaccess.*;
import model.GameSummary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.*;
import result.CreateGameResult;
import result.JoinGameResult;
import result.ListGamesResult;
import result.RegisterResult;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
class GameServiceTest {

    private  GameService gameService;
    private RegisterResult testUser;
    private UserDAO userDAO = new MemoryUserDAO();
    private AuthDAO authDAO = new MemoryAuthDAO();
    private GameDAO gameDAO = new MemoryGameDAO();

    @BeforeEach
    public void setUp() throws DataAccessException {
        gameService = new GameService(authDAO, gameDAO);
        UserService userService = new UserService(userDAO, authDAO);
        testUser = userService.register(new RegisterRequest("Test User", "testPassword", "testEmail@mail.com"));
    }

    @Test
    void createGamePositive() throws DataAccessException {
        CreateGameResult result = gameService.createGame(testUser.authToken(), new CreateGameRequest("Test Game"));
        int expectedID = 1;
        int actualID = result.gameID();
        assertEquals(expectedID, actualID);
    }

    @Test
    void createGameNegative() {
        DataAccessException exception = assertThrows(DataAccessException.class, () -> {
            gameService.createGame(null, new CreateGameRequest("Test Game"));
        });
        assertEquals("Error: bad request", exception.getMessage());
    }

    @Test
    void joinGamePositive() throws DataAccessException {
        CreateGameResult createGameResult = gameService.createGame(testUser.authToken(), new CreateGameRequest("Test Game"));
        JoinGameResult actual = gameService.joinGame(testUser.authToken(), new JoinGameRequest("WHITE", 1));
        JoinGameResult expected = new JoinGameResult();
        assertEquals(expected, actual);

    }

    @Test
    void joinGameNegative() throws DataAccessException {
        CreateGameResult createGameResult = gameService.createGame(testUser.authToken(), new CreateGameRequest("Test Game"));
        DataAccessException exception = assertThrows(DataAccessException.class, () -> {
            gameService.joinGame(testUser.authToken(), new JoinGameRequest("WHITE", 2));
        });
        assertEquals("Error: bad request", exception.getMessage());
    }

    @Test
    void listGamesPositive() throws DataAccessException {
        CreateGameResult createGameResult = gameService.createGame(testUser.authToken(), new CreateGameRequest("Test Game Name"));
        ListGamesResult actual = gameService.listGames(testUser.authToken(), new ListGamesRequest(testUser.authToken()));
        ArrayList<GameSummary> gameSummaries = new ArrayList<>();
        gameSummaries.add(new GameSummary(1, null, null, "Test Game Name"));
        ListGamesResult expected = new ListGamesResult(gameSummaries);
        assertEquals(expected, actual);
    }

    @Test
    void listGamesNegative() {
        DataAccessException exception = assertThrows(DataAccessException.class, () -> {
            gameService.listGames(null, null);
        });
        assertEquals("Error: bad request", exception.getMessage());
    }

    @Test
    void clear() throws DataAccessException {
        CreateGameResult createGameResult = gameService.createGame(testUser.authToken(), new CreateGameRequest("Test Game Name"));
        assertDoesNotThrow(() -> {
            gameService.clear();
        });
    }
}