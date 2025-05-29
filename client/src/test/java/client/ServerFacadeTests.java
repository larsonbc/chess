package client;

import dataaccess.DataAccessException;
import exception.ResponseException;
import org.junit.jupiter.api.*;
import request.CreateGameRequest;
import request.LoginRequest;
import request.LogoutRequest;
import request.RegisterRequest;
import result.CreateGameResult;
import result.RegisterResult;
import server.Server;
import server.ServerFacade;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;

    @BeforeAll
    public static void init() throws DataAccessException {
        server = new Server();
        server.clear();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade("http://localhost:" + port);
    }

    @BeforeEach
    public void setup() throws DataAccessException {
        server.clear();
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @Test
    public void registerPositive() throws ResponseException {
        var authData = facade.register(new RegisterRequest("player1", "password", "p1@email.com"));
        assertTrue(authData.authToken().length() > 10);
    }

    @Test
    public void registerNegative() {
        assertDoesNotThrow(() -> {
            facade.register(new RegisterRequest("player1", "password", "p1@email.com"));
        });
        assertThrows(ResponseException.class, () -> facade.register(new RegisterRequest("player1", "password", "p1@email.com")));
    }

    @Test
    public void loginPositive() throws ResponseException {
        var authData = facade.register(new RegisterRequest("player1", "password", "p1@email.com"));
        assertTrue(authData.authToken().length() > 10);
        assertDoesNotThrow(() -> {
            facade.login(new LoginRequest("player1", "password"));
        });
    }

    @Test
    public void loginNegative() {
        assertDoesNotThrow(() -> {
            facade.register(new RegisterRequest("player1", "password", "p1@email.com"));
        });
        assertThrows(ResponseException.class, () -> facade.login(new LoginRequest("player1", "incorrect")));
    }

    @Test
    public void logoutPositive() throws ResponseException {
        var authData = facade.register(new RegisterRequest("player1", "password", "p1@email.com"));
        assertTrue(authData.authToken().length() > 10);
        String authToken = authData.authToken();
        facade.logout(new LogoutRequest(authToken));

        assertThrows(ResponseException.class, () -> facade.createGame(new CreateGameRequest("ShouldNotWork"), authToken));
    }

    @Test public void logoutNegative() {
        String badToken = "123abc";
        assertThrows(ResponseException.class, () -> facade.logout(new LogoutRequest(badToken)));
    }

    @Test
    public void createGamePositive() throws ResponseException {
        RegisterResult newUser = facade.register(new RegisterRequest("player1", "password", "p1@email.com"));
        CreateGameResult newGame = facade.createGame(new CreateGameRequest("testGame"), newUser.authToken());
        int expectedID = 1;
        int actualID = newGame.gameID();
        assertEquals(expectedID, actualID);
    }

    @Test
    public void createGameNegative() {
        assertThrows(ResponseException.class, () -> facade.createGame(new CreateGameRequest("ShouldNotWork"), "badToken"));
    }

}
