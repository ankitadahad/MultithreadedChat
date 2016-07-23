package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashSet;

public class Writer implements Runnable {
    public static HashSet<String> COMMANDS = new HashSet<String>();
    ChatUI chatUI;

    static {
        COMMANDS.add("SAVE");
        COMMANDS.add("CLEAR");
        COMMANDS.add("GET");
    }

    private final BufferedReader stdin;
    private final PrintWriter out;
    private final String userName;

    Writer(BufferedReader stdIn, PrintWriter out, String userName, ChatUI chatUI) {
        this.stdin = stdIn;
        this.out = out;
        this.userName = userName;
        this.chatUI = chatUI;
    }

    public void run() {
        try {
            String userInput;
            out.println(userName);
            while ((userInput = stdin.readLine()) != null) {
                if (userInput.equalsIgnoreCase("exit")) {
                    stdin.close();
                    System.exit(1);
                } else {
                    String temp[] = userInput.split(":");
                    if (COMMANDS.contains(temp[0])) {
                        out.println(userInput);
                    } else {
                        out.println(userName + " :    " + userInput + "\t" + new Date().toString());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}