import chess.*;
import dataaccess.*;
import server.Server;
import service.GameService;
import service.UserService;

public class Main {
    public static void main(String[] args) throws DataAccessException {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Server: " + piece);

        Server server = new Server();
        server.run(8080);


//        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
//        System.out.println("♕ 240 Chess Server: " + piece);
//
//        try {
//            var port = 8080;
//            //    UserDAO userDAO = new MemoryUserDAO();
//            //    AuthDAO authDAO = new MemoryAuthDAO();
//            //    GameDAO gameDAO = new MemoryGameDAO();
//            SQLUserDAO userDAO = new SQLUserDAO();
//            SQLAuthDAO authDAO = new SQLAuthDAO();
//            SQLGameDAO gameDAO = new SQLGameDAO();
//
//            UserService userService = new UserService(userDAO, authDAO);
//            GameService gameService = new GameService(authDAO, gameDAO);
//            Server server = new Server(userService, gameService);
//            server.run(port);
//        } catch (DataAccessException ex) {
//            //throw new RuntimeException(e);
//            System.out.printf("Unable to start server: %s%n", ex.getMessage());
//        }
    }
}