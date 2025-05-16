package server;

import dataaccess.*;
import handler.ClearDBHandler;
import handler.LoginHandler;
import handler.LogoutHandler;
import handler.RegisterHandler;
import service.UserService;
import spark.*;

public class Server {

    UserDAO userDAO = new MemoryUserDAO();
    AuthDAO authDAO = new MemoryAuthDAO();
    UserService userService = new UserService(userDAO, authDAO);
    RegisterHandler registerHandler = new RegisterHandler(userService);
    LoginHandler loginHandler = new LoginHandler(userService);
    LogoutHandler logoutHandler = new LogoutHandler(userService);
    ClearDBHandler clearDBHandler = new ClearDBHandler(userService);

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

        Spark.exception(DataAccessException.class, this::errorHandler);

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void errorHandler(DataAccessException e, Request req, Response res) {
        res.status(e.StatusCode());
        res.body(e.toJson());
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
