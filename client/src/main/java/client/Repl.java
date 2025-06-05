package client;

import server.ServerFacade;

import java.util.Scanner;

public class Repl {
    private final ServerFacade facade;
    private final PreloginClient preloginClient;
    private PostloginClient postloginClient;
    private final StateHandler stateHandler = new StateHandler();
    private GameplayClient gameplayClient;

    public Repl(String serverUrl) {
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
                if (currentState == State.SIGNEDOUT) {
                    result = preloginClient.eval(line);
                    if (stateHandler.getState() == State.SIGNEDIN) {
                        postloginClient = new PostloginClient(stateHandler, facade);
                    }
                } else if (currentState == State.SIGNEDIN) {
                    result = postloginClient.eval(line);
                    if (stateHandler.getState() == State.GAMEPLAY) {
                        gameplayClient = new GameplayClient(stateHandler);
                    }
                } else if (currentState == State.GAMEPLAY) {
                    result = gameplayClient.eval(line);
                }

//                if (stateHandler.getState() == State.SIGNEDOUT) {
//                    result = preloginClient.eval(line);
//                    if (stateHandler.getState() == State.SIGNEDIN) {
//                        postloginClient = new PostloginClient(stateHandler, facade);
//                    }
//                } else {
//                    result = postloginClient.eval(line);
//                }
                System.out.print("\u001b[34m" + result);
            } catch (Throwable e) {
                var msg = e.getMessage();
                System.out.print("\u001b[31m" + msg + "\u001b[0m");
            }
        }
        System.out.println();
    }

    private void printPrompt() {
        if (stateHandler.getState() == State.SIGNEDOUT) {
            System.out.print("\n" + "\u001b[0m" + "Chess Login >>> " + "\u001b[32m");
        } else if (stateHandler.getState() == State.SIGNEDIN) {
            System.out.print("\n" + "\u001b[0m" + "Chess >>> " + "\u001b[32m");
        } else if (stateHandler.getState() == State.GAMEPLAY) {
            System.out.print("\n" + "\u001b[0m" + "Chess Game >>> " + "\u001b[32m");
        }
    }
}
