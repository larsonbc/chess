package server;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import handler.RegisterHandler;
import service.AuthService;
import service.GameService;
import service.UserService;
import spark.*;

public class Server {

    MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();
    MemoryGameDAO memoryGameDAO = new MemoryGameDAO();
    MemoryUserDAO memoryUserDAO = new MemoryUserDAO();
    AuthService authService = new AuthService(memoryAuthDAO);
    GameService gameService = new GameService(memoryGameDAO, memoryAuthDAO);
    UserService userService = new UserService(memoryUserDAO, memoryAuthDAO);

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.get("/hello", (req, res) -> "Hello World");

//        Spark.delete("/db", this::clear);
//        Spark.post("/user", (req, res) -> (new RegisterHandler(userService).handleRequest(req, res));
//        Spark.post("/session", this::login);
//        Spark.delete("/session", this::logout);
//        Spark.get("/game", this::listGames);
//        Spark.post("/game", this::createGame);
//        Spark.put("/game", this::joinGame);




        //call to createRoutes() method here

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    public static void main(String[] args) {
        new Server().run(8080);
    }
}
