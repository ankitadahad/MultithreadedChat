package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/*import client.ChatUI.clearMessageButtonListener;
import client.ChatUI.deleteMessageButtonListener;
import client.ChatUI.getMessageButtonListener;
import client.ChatUI.saveMessageButtonListener;
import client.ChatUI.sendMessageButtonListener;
*/
/**
 * UI for Chat clients
 * UI will ask for 3 inputs
 */
public class ChatUI {

    //UI Components
    JButton sendMessage;
    JButton saveMessage;
    JButton clearMessage;
    JButton getMessage;
    JButton deleteMessage;
    JTextField messageBox;
    JTextArea chatBox;
    JFrame mainPanel = new JFrame("DS Assignment");

    //Variables from main class
    BufferedReader stdIn;
    PrintWriter out;
    String userName;

    public ChatUI(PrintWriter out, String userName, BufferedReader stdIn) {
        this.out = out;
        this.userName = userName;
        this.stdIn = stdIn;
    }

    public void display() {
        mainPanel.setTitle(userName);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel southPanel = new JPanel();
        southPanel.setBackground(Color.LIGHT_GRAY);
        southPanel.setLayout(new GridBagLayout());

        JPanel eastPanel = new JPanel();
        eastPanel.setBackground(Color.LIGHT_GRAY);
        eastPanel.setLayout(new GridLayout(5, 1));

        messageBox = new JTextField(30);
        messageBox.requestFocusInWindow();

        sendMessage = new JButton("Send Message");
        sendMessage.addActionListener(new sendMessageButtonListener());

        saveMessage = new JButton("Save");
        saveMessage.addActionListener(new saveMessageButtonListener());

        clearMessage = new JButton("clear");
        clearMessage.addActionListener(new clearMessageButtonListener());

        getMessage = new JButton("get");
        getMessage.addActionListener(new getMessageButtonListener());

        deleteMessage = new JButton("<html>delete<br />history</html>");
        deleteMessage.addActionListener(new deleteMessageButtonListener());

        chatBox = new JTextArea();
        chatBox.setEditable(false);
        chatBox.setFont(new Font("Serif", Font.PLAIN, 15));
        chatBox.setLineWrap(true);

        mainPanel.add(new JScrollPane(chatBox), BorderLayout.CENTER);

        GridBagConstraints left = new GridBagConstraints();
        left.anchor = GridBagConstraints.LINE_START;
        left.fill = GridBagConstraints.HORIZONTAL;
        left.weightx = 512.0D;
        left.weighty = 1.0D;

        //Creating Grid
        GridBagConstraints right = new GridBagConstraints();
        right.insets = new Insets(0, 10, 0, 0);
        right.anchor = GridBagConstraints.LINE_END;
        right.fill = GridBagConstraints.NONE;
        right.weightx = 1.0D;
        right.weighty = 1.0D;

        //Adding all the buttons
        southPanel.add(messageBox, left);
        southPanel.add(sendMessage, right);
        eastPanel.add(saveMessage);
        eastPanel.add(clearMessage);
        eastPanel.add(getMessage);
        eastPanel.add(deleteMessage);

        mainPanel.add(BorderLayout.SOUTH, southPanel);
        mainPanel.add(BorderLayout.EAST, eastPanel);

        //Adding to main panel
        this.mainPanel.add(mainPanel);
        this.mainPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainPanel.setSize(470, 300);
        this.mainPanel.setVisible(true);
    }

    public JTextArea getChatBox() {
        return this.chatBox;
    }

    class sendMessageButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (messageBox.getText().length() < 1) {
                // do nothing
            } else {
                out.println("<" + userName + ">:  " + messageBox.getText() + "\t" + new Date().toString() + "\n");
                messageBox.setText("");
            }
            messageBox.requestFocusInWindow();
        }
    }

    //Handler for save self chat
    class saveMessageButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            out.println("SAVE");
        }
    }

    //Handler for clear screen
    class clearMessageButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            chatBox.setText("");
        }
    }

    //Handler for GET Specified user history. user will be picked from message box
    class getMessageButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            out.println("GET:" + messageBox.getText().trim());
            messageBox.setText("");
        }
    }

    //Handler for deleting chat history for current client
    class deleteMessageButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            out.println("CLEAR");
        }
    }
}
