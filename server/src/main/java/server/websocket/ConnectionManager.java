package server.websocket;

import chess.ChessGame;
import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    public final ConcurrentHashMap<String, Connection> connections = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Integer> userGameMap = new ConcurrentHashMap<>();

    public void add(String username, Session session, int gameID) {
        var connection = new Connection(username, session);
        connections.put(username, connection);
        userGameMap.put(username, gameID);
    }

    public void remove(String username) {
        connections.remove(username);
        userGameMap.remove(username);
    }

    public void sendLoadGame(String username, ChessGame game, int gameID, boolean sendToAll) throws IOException {
        LoadGameMessage loadGameMessage = new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME);
        loadGameMessage.setGame(game);
        String json = new Gson().toJson(loadGameMessage);

        for (var entry : connections.entrySet()) {
            String currentUser = entry.getKey();
            Connection connection = entry.getValue();
            Integer userGameID = userGameMap.get(currentUser);

            if (connection.session.isOpen()) {
                if (!sendToAll) {
                    if (currentUser.equals(username)) {
                        connection.send(json);
                    }
                } else if (userGameID != null && userGameID == gameID) {
                    connection.send(json);
                }
            }
        }
    }


    public void sendError(String username, String msg) throws IOException {
        ErrorMessage errorMessage = new ErrorMessage(ServerMessage.ServerMessageType.ERROR);
        errorMessage.setErrorMessage(msg);
        var conn = connections.get(username);
        conn.send(new Gson().toJson(errorMessage));
    }

    public void sendResignNotification(String username, int gameID) throws IOException {
        NotificationMessage notificationMessage = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        notificationMessage.setMessage(username + " has resigned");
        String json = new Gson().toJson(notificationMessage);

        var removeList = new ArrayList<Connection>();
        for (var entry : connections.entrySet()) {
            String currentUser = entry.getKey();
            Connection connection = entry.getValue();
            Integer userGameID = userGameMap.get(currentUser);

            if (connection.session.isOpen()) {
                if (userGameID != null && userGameID == gameID) {
                    connection.send(json);
                }
            } else {
                removeList.add(connection);
            }
        }

        // Clean up any closed sessions
        for (var c : removeList) {
            connections.remove(c.username);
            userGameMap.remove(c.username);
        }
    }


    public void broadCastToGame(int gameID, String excludeName, String message) {
        NotificationMessage notificationMessage = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        notificationMessage.setMessage(message);
        String json = new Gson().toJson(notificationMessage);
        var removeList = new ArrayList<Connection>();

        for (var entry : connections.entrySet()) {
            String username = entry.getKey();
            Connection connection = entry.getValue();
            Session session = connection.session;
            Integer userGameID = userGameMap.get(username);

            if (connection.session.isOpen()) {
                if (!username.equals(excludeName) && userGameID != null && userGameID == gameID) {
                    try {
                        connection.send(json);
                    } catch (IOException e) {
                        System.err.printf("Failed to send message to %s: %s%n", username, e.getMessage());
                    }
                }
            } else {
                removeList.add(connection);
            }
        }
        for (var c : removeList) {
            connections.remove(c.username);
            userGameMap.remove(c.username);
        }
    }

}
