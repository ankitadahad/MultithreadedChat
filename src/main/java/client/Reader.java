package client;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Reader Thread which will keep listening to the server.
 * When Message comes from Server it will append the message to
 * text area in chat UI
 */
public class Reader implements Runnable {
    ChatUI chatUI;
    private final BufferedReader in;

    Reader(BufferedReader in, ChatUI chatUI) {
        this.in = in;
        this.chatUI = chatUI;
    }

    public void run() {
        String output;
        try {
            while ((output = in.readLine()) != null) {
                System.out.println(output);
                chatUI.getChatBox().append("\n" + output);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
