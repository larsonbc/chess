package client;

import client.websocket.ServerMessageObserver;
import com.google.gson.Gson;
import server.ServerFacade;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;
import client.websocket.WebSocketFacade;

import java.util.Scanner;

public class Repl implements ServerMessageObserver {
    private final String serverUrl;
    private final ServerFacade facade;
    private final PreloginClient preloginClient;
    private PostloginClient postloginClient;
    private final StateHandler stateHandler = new StateHandler();
    //private WebSocketFacade ws;

    public Repl(String serverUrl) {
        this.serverUrl = serverUrl;
        facade = new ServerFacade(serverUrl);
        preloginClient = new PreloginClient(stateHandler, facade);
    }

    public void run() {
        System.out.println("♕ Welcome to Chess. Sign in to start. ♕");
        System.out.println(preloginClient.help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                State currentState = stateHandler.getState();
//                if (currentState == State.SIGNEDOUT) {
//                    result = preloginClient.eval(line);
//                    if (stateHandler.getState() == State.SIGNEDIN) {
//                        postloginClient = new PostloginClient(stateHandler, facade);
//                    }
//                } else if (currentState == State.SIGNEDIN) {
//                    result = postloginClient.eval(line);
//                    if (stateHandler.getState() == State.GAMEPLAY) {
//                        gameplayClient = new GameplayClient(stateHandler);
//                    }
//                } else if (currentState == State.GAMEPLAY) {
//                    result = gameplayClient.eval(line);
//                }
                switch (currentState) {
                    case SIGNEDOUT -> {
                        result = preloginClient.eval(line);
                        if (stateHandler.getState() == State.SIGNEDIN) {
                            WebSocketFacade ws = new WebSocketFacade(serverUrl, this);
                            stateHandler.setWs(ws);
                            postloginClient = new PostloginClient(stateHandler, facade);
                        }
                    }
                    case SIGNEDIN -> {
                        result = postloginClient.eval(line);
                    }
                    case GAMEPLAY -> {
                        result = stateHandler.getGameplayClient().eval(line);
                    }
                }
                System.out.print("\u001b[34m" + result);
            } catch (Throwable e) {
                var msg = e.getMessage();
                System.out.print("\u001b[31m" + msg + "\u001b[0m");
            }
        }
        System.out.println();
    }

    private void printPrompt() {
//        if (stateHandler.getState() == State.SIGNEDOUT) {
//            System.out.print("\n" + "\u001b[0m" + "Chess Login >>> " + "\u001b[32m");
//        } else if (stateHandler.getState() == State.SIGNEDIN) {
//            System.out.print("\n" + "\u001b[0m" + "Chess >>> " + "\u001b[32m");
//        } else if (stateHandler.getState() == State.GAMEPLAY) {
//            System.out.print("\n" + "\u001b[0m" + "Chess Game >>> " + "\u001b[32m");
//        }
        String prompt = switch (stateHandler.getState()) {
            case SIGNEDOUT -> "Chess Login >>> ";
            case SIGNEDIN -> "Chess >>> ";
            case GAMEPLAY -> "Chess Game >>> ";
        };
        System.out.print("\n\u001b[0m" + prompt + "\u001b[32m");
    }

    @Override
    public void notify(ServerMessage message) {
        switch (message.getServerMessageType()) {
            case NOTIFICATION -> {
                NotificationMessage msg = (NotificationMessage) message;
                System.out.println("\u001b[33m" + msg.getMessage()); // Yellow
            }
            case ERROR -> {
                ErrorMessage error = (ErrorMessage) message;
                System.out.println("\u001b[31m" + error.getErrorMessage()); // Red
            }
            case LOAD_GAME -> {
                LoadGameMessage msg = (LoadGameMessage) message;
                System.out.println("\u001b[33m" + "Load game message"); // Red
            }
            default -> {
                System.out.println("\u001b[33m" + "Received unknown message type."); // Yellow
            }
        }

        printPrompt();
    }
}
