package server;

import dataaccess.AuthDAO;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import dataaccess.UserDAO;
import handler.ClearDBHandler;
import handler.RegisterHandler;
import service.UserService;
import spark.*;

public class Server {

    UserDAO userDAO = new MemoryUserDAO();
    AuthDAO authDAO = new MemoryAuthDAO();
    UserService userService = new UserService(userDAO, authDAO);
    RegisterHandler registerHandler = new RegisterHandler(userService);
    ClearDBHandler clearDBHandler = new ClearDBHandler(userService);

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.

        //This line initializes the server and can be removed once you have a functioning endpoint 
        //Spark.init();

        Spark.post("/user", (req, res) -> (registerHandler.handleRequest(req, res)));
        Spark.delete("/db", (req, res) -> (clearDBHandler.handleRequest(req, res)));

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
