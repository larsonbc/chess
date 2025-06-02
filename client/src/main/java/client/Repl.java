package client;

import server.ServerFacade;

import java.util.Scanner;

public class Repl {
    private final ServerFacade facade;
    private final PreloginClient preloginClient;
    private PostloginClient postloginClient;
    private final StateHandler stateHandler = new StateHandler();

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
                if (stateHandler.getState() == State.SIGNEDOUT) {
                    result = preloginClient.eval(line);
                    if (stateHandler.getState() == State.SIGNEDIN) {
                        postloginClient = new PostloginClient(stateHandler, facade);
                    }
                } else {
                    result = postloginClient.eval(line);
                }
                System.out.print("\u001b[34m" + result);
            } catch (Throwable e) {
                //var msg = e.toString();
                var msg = e.getMessage();
                System.out.print("\u001b[31m" + msg + "\u001b[0m");
            }
        }
        System.out.println();
    }

    private void printPrompt() {
        if (stateHandler.getState() == State.SIGNEDOUT) {
            System.out.print("\n" + "\u001b[0m" + "Chess Login >>> " + "\u001b[32m");
        }
        if (stateHandler.getState() == State.SIGNEDIN) {
            System.out.print("\n" + "\u001b[0m" + "Chess >>> " + "\u001b[32m");
        }
    }
}
