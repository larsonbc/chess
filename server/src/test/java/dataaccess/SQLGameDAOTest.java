package dataaccess;

import model.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SQLGameDAOTest {

    private SQLGameDAO gameDAO;

    @BeforeEach
    public void setUp() throws DataAccessException {
        gameDAO = new SQLGameDAO();
        gameDAO.clear();
    }

    @Test
    void createGamePositive() {
        assertDoesNotThrow(() -> {
            gameDAO.createGame("Test Game Name");
        });
    }

    @Test
    void createGameNegative() {
    }

    @Test
    void getGamePositive() throws DataAccessException {
        GameData newGame = gameDAO.createGame("Test Game Name");
        GameData result = gameDAO.getGame(1);
        assertEquals(newGame.gameName(), result.gameName());
    }

    @Test
    void getGameNegative() throws DataAccessException {
    }

    @Test
    void listGamesPositive() throws DataAccessException {
        GameData newGame = gameDAO.createGame("Test Game Name");
        assertNotNull(gameDAO.listGames());
    }

    @Test
    void listGamesNegative() throws DataAccessException {
    }

    @Test
    void updateGamePositive() throws DataAccessException {
        GameData newGame = gameDAO.createGame("Test Game Name");
        assertTrue(gameDAO.updateGame("WHITE", 1, "Test Name"));
    }

    @Test
    void updateGameNegative() throws DataAccessException {
    }

    @Test
    void clear() throws DataAccessException {
        GameData newGame = gameDAO.createGame("Test Game Name");
        assertDoesNotThrow(() -> {
            gameDAO.clear();
        });
    }
}