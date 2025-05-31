package client;

import java.util.Scanner;

public class Repl {

    private final PreloginClient preloginClient;
    private PostloginClient postloginClient;
    private StateHandler stateHandler = new StateHandler();

    public Repl(String serverUrl) {
        preloginClient = new PreloginClient(stateHandler);
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
                        postloginClient = new PostloginClient(stateHandler);
                    }
                } else {
                    result = postloginClient.eval(line);
                    if (stateHandler.getState() == State.SIGNEDOUT) {
                        System.out.println(preloginClient.help());
                    }
                }
                System.out.print("\u001b[34m" + result);
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
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
