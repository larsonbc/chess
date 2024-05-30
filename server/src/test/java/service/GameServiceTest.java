package service;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.JoinGameRequest;

import java.util.ArrayList;

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
    void testCreateGameUnauthorized() throws DataAccessException {
        Exception exception = assertThrows(DataAccessException.class, () -> { gameService.createGame("fake Auth", "Test Game Name"); });
        String expectedMessage = "Error: unauthorized";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testListSingleGame() throws DataAccessException {
        int gameID = gameService.createGame(testAuth.authToken(), "Test Game");
        ArrayList<GameData> expected = new ArrayList<>();
        expected.add(new GameData(1, null, null, "Test Game", new ChessGame()));
        ArrayList<GameData> actual = gameService.listGames(testAuth.authToken());
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void testListGameUnauthorized() throws DataAccessException {
        Exception exception = assertThrows(DataAccessException.class, () -> { gameService.listGames("Fake Auth"); });
        String expectedMessage = "Error: unauthorized";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testJoinGameWhite() throws DataAccessException {
        int newGameID = gameService.createGame(testAuth.authToken(), "New Game");
        gameService.joinGame(new JoinGameRequest(testAuth.authToken(), ChessGame.TeamColor.WHITE, newGameID));
        ArrayList<GameData> expected = new ArrayList<>();
        expected.add(new GameData(1, "TestUser", null, "New Game", new ChessGame()));
        ArrayList<GameData> actual = gameService.listGames(testAuth.authToken());
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void testJoinGameWhiteUnauthorized() throws DataAccessException {
        Exception exception = assertThrows(DataAccessException.class, () -> { gameService.joinGame(new JoinGameRequest("FakeAuthToken", ChessGame.TeamColor.WHITE, 1)); });
        String expectedMessage = "Error: unauthorized";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void deleteAllGames() {
    }
}