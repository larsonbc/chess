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

    public void add(String username, Session session) {
        var connection = new Connection(username, session);
        connections.put(username, connection);
    }

    public void remove(String username) {
        connections.remove(username);
    }

    public void broadcast(String excludeName, String msg) throws IOException { //change notification type from object to what it should be (Notification?)
        NotificationMessage notificationMessage = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        notificationMessage.setMessage(msg);
        var removeList = new ArrayList<Connection>();
        for (var c : connections.values()) {
            if (c.session.isOpen()) {
                if (!c.username.equals(excludeName)) {
                    c.send(new Gson().toJson(notificationMessage));
                }
            } else {
                removeList.add(c);
            }
        }

        // Clean up any connections that were left open.
        for (var c : removeList) {
            connections.remove(c.username);
        }
    }

    public void sendLoadGame(String username, ChessGame game, boolean sendToAll) throws IOException {
        LoadGameMessage loadGameMessage = new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME);
        loadGameMessage.setGame(game);
        for (var c : connections.values()) {
            if (c.session.isOpen()) {
                if (!sendToAll) {
                    if (c.username.equals(username)) {
                        c.send(new Gson().toJson(loadGameMessage));
                    }
                } else {
                    c.send(new Gson().toJson(loadGameMessage));
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

}
