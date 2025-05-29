package client;

import java.util.Scanner;

public class Repl {

    private final PreloginClient preloginClient;
    private final PostloginClient postloginClient;
    private State state = State.SIGNEDOUT;

    public Repl(String serverUrl) {
        preloginClient = new PreloginClient();
        postloginClient = new PostloginClient();
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
                result = preloginClient.eval(line);
                System.out.print("\u001b[34m" + result);
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();
    }

    private void printPrompt() {
        System.out.print("\n" + "\u001b[0m" + ">>> " + "\u001b[32m");
    }
}
