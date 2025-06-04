package server.websocket;

import chess.ChessGame;
import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import service.UserService;
import websocket.commands.UserGameCommand;
import websocket.messages.ServerMessage;

import java.io.IOException;

@WebSocket
public class WebSocketHandler {

    private final ConnectionManager connections = new ConnectionManager();
    private final UserService userService;

    public WebSocketHandler(UserService userService) {
        this.userService = userService;
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String msg) {
        try {
            UserGameCommand command = new Gson().fromJson(msg, UserGameCommand.class);

            // Throws a custom Unauthorized Exception - mine may work differently
            String username = userService.getUsernameFromAuth(command.getAuthToken());

            saveSession(command.getGameID(), session);

            switch (command.getCommandType()) {
                case CONNECT -> connect(session, username, (UserGameCommand) command); //change from UserGameCommand to Connect Command
//                case MAKE_MOVE -> makeMove(session, username, (MakeMoveCommand) command);
//                case LEAVE -> leaveGame(session, username, (LeaveGameCommand) command);
//                case RESIGN -> resign(session, username, (ResignCommand) command);
                default -> System.out.println("Not able to do stuff");

            }
//        } catch (UnauthorizedException ex) {
//            // Serializes and sends the error message
//            sendMessage(session.getRemote(), new ErrorMessage("Error: unauthorized"));
//        }
        } catch (Exception ex) {
            ex.printStackTrace();
            //sendMessage(session.getRemote(), new ErrorMessage("Error: " + ex.getMessage()));
        }
    }

    private void connect(Session session, String username, UserGameCommand command) throws IOException {
        connections.add(username, session);
        ChessGame game = new ChessGame(); // may need to change to get game from server with command game ID
        connections.sendLoadGame(username, game);
        var message = String.format("%s has joined the game", username);
        connections.broadcast(username, message);
    }

    private String getUsername(String authToken) {
        return "Test_Username";
    }

    private void saveSession(int gameID, Session session) {
        //make sure session is in connection manager
    }
}
