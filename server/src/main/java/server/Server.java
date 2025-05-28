package server;

import dataaccess.*;
import handler.*;
import service.GameService;
import service.UserService;
import spark.*;

public class Server {

    UserDAO userDAO;
    AuthDAO authDAO;
    GameDAO gameDAO;
    UserService userService;
    GameService gameService;
    RegisterHandler registerHandler;
    LoginHandler loginHandler;
    LogoutHandler logoutHandler;
    ClearDBHandler clearDBHandler;
    CreateGameHandler createGameHandler;
    JoinGameHandler joinGameHandler;
    ListGamesHandler listGamesHandler;

    public Server() {
        try {
            userDAO = new SQLUserDAO();
            authDAO = new SQLAuthDAO();
            gameDAO  = new SQLGameDAO();
        } catch (DataAccessException e) {
            System.out.println("Unable to create SQL DAO(s), using memory DAO(s)");
            userDAO = new MemoryUserDAO();
            authDAO = new MemoryAuthDAO();
            gameDAO = new MemoryGameDAO();
        }
        userService = new UserService(userDAO, authDAO);
        gameService = new GameService(authDAO, gameDAO);
        registerHandler = new RegisterHandler(userService);
        loginHandler = new LoginHandler(userService);
        logoutHandler = new LogoutHandler(userService);
        clearDBHandler = new ClearDBHandler(userService, gameService);
        createGameHandler = new CreateGameHandler(gameService);
        joinGameHandler = new JoinGameHandler(gameService);
        listGamesHandler = new ListGamesHandler(gameService);
    }

    public int run(int desiredPort) {

        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.

        //This line initializes the server and can be removed once you have a functioning endpoint 
        //Spark.init();

        Spark.post("/user", (req, res) -> (registerHandler.handleRequest(req, res)));
        Spark.delete("/db", (req, res) -> (clearDBHandler.handleRequest(req, res)));
        Spark.post("/session", (req, res) -> (loginHandler.handleLogin(req, res)));
        Spark.delete("/session", (req, res) -> (logoutHandler.handleLogout(req, res)));
        Spark.post("/game", (req, res) -> (createGameHandler.handleCreateGame(req, res)));
        Spark.put("/game", (req, res) -> (joinGameHandler.handleJoinGame(req, res)));
        Spark.get("/game", (req, res) -> (listGamesHandler.handleListGames(req, res)));

        Spark.exception(DataAccessException.class, this::errorHandler);

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void errorHandler(DataAccessException e, Request req, Response res) {
        res.status(e.getStatusCode());
        res.body(e.toJson());
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    public void clear() throws DataAccessException {
        userDAO.clearUsers();
        authDAO.clear();
        gameDAO.clear();
    }
}
