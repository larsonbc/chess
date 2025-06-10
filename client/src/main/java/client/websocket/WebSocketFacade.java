package client.websocket;

import chess.ChessMove;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import exception.ResponseException;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketFacade extends Endpoint {
    Session session;
    ServerMessageObserver messageObserver;

    public WebSocketFacade(String url, ServerMessageObserver messageObserver) throws ResponseException {
        try {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/ws");
            this.messageObserver = messageObserver;

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            //set message handler
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    JsonObject jsonObject = JsonParser.parseString(message).getAsJsonObject();
                    ServerMessage.ServerMessageType type = ServerMessage.ServerMessageType.valueOf(jsonObject.get("serverMessageType").getAsString());
                    ServerMessage parsedMessage;
                    Gson gson = new Gson();
                    switch (type) {
                        case NOTIFICATION -> parsedMessage = gson.fromJson(message, NotificationMessage.class);
                        case ERROR -> parsedMessage = gson.fromJson(message, ErrorMessage.class);
                        case LOAD_GAME -> parsedMessage = gson.fromJson(message, LoadGameMessage.class);
                        default -> parsedMessage = gson.fromJson(message, ServerMessage.class); // fallback
                    }
                    messageObserver.notify(parsedMessage);
                }
            });
        } catch (DeploymentException | IOException | URISyntaxException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    //Endpoint requires this method, but you don't have to do anything
    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {

    }

    public void joinGame(String authToken, int gameID, String color) {
        try {
            var command = new UserGameCommand(UserGameCommand.CommandType.CONNECT, authToken, gameID, color);
            this.session.getBasicRemote().sendText(new Gson().toJson(command));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public void leaveGame(String authToken, int gameID) {
        try {
            var command = new UserGameCommand(UserGameCommand.CommandType.LEAVE, authToken, gameID, null);
            this.session.getBasicRemote().sendText(new Gson().toJson(command));
            //this.session.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void resign(String authToken, int gameID) {
        try {
            var command = new UserGameCommand(UserGameCommand.CommandType.RESIGN, authToken, gameID, null);
            this.session.getBasicRemote().sendText(new Gson().toJson(command));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public void makeMove(String authToken, int gameID, ChessMove move) {
        try {
            var command = new MakeMoveCommand(UserGameCommand.CommandType.MAKE_MOVE, authToken, gameID, null);
            command.setMove(move);
            this.session.getBasicRemote().sendText(new Gson().toJson(command));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
