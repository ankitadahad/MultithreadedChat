package client;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashSet;

import javax.swing.JOptionPane;

/**
 * Main class for executing chat client and spinning a thread to perform specific operations
 * this class will render chat window and ask for configuration.
 * once the connection is setup the user can start chatting
 */
public class ChatClient {

    public static void main(String[] args) throws IOException {
        String temp[] = new String[2];
        while (temp.length < 3) {
            String input = JOptionPane.showInputDialog(
                    "Enter <IpAddress> : <Port Number> : <Username>");
            temp = input.split(":");
        }

        String ipAddress = temp[0];
        int portNumber = Integer.parseInt(temp[1]);
        String username = temp[2];

        try {
            Socket socket1 = new Socket(ipAddress, portNumber);
            PrintWriter out = new PrintWriter(socket1.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            //Construct a chat UI using writer stream and reader stream
            ChatUI chatUI = new ChatUI(out, username, stdIn);
            //display chat box
            chatUI.display();
            //Reader thread. we will pass the chat UI object to this so reader can write to the text area of the client UI
            new Thread(new Reader(in, chatUI)).start();
            //Writer thread from previous version of this application.
            //Since we have UI now. UI will take care of writing. Following thread is orphan and no longer needed
            new Thread(new Writer(stdIn, out, username, chatUI)).start();

        } catch (UnknownHostException e) {
            System.err.println(" Host not found. please check your ip. " + ipAddress);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    ipAddress);
        }
    }
}


