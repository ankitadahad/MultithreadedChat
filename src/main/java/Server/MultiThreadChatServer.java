package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.Semaphore;

public class MultiThreadChatServer implements Runnable {
	private Socket connection;
	private int ID;
	static Vector<PrintWriter> userBucket = new Vector();

	ArrayList broadcastList = new ArrayList<>();
	//UserHistory is shared amongst all the users to share their chat
	public static HashMap<String, List<String>> userHistory = new HashMap<>();
	//Mutex is used to make sure that only one thread at a time gets access to userHistory 
	public static Semaphore mutex = new Semaphore(1);

	/*
	 * Main function has following functionality :
	 * To take Port number from user and bind Server to it
	 * Server listens on provided port
	 * Server delegates separate thread for each connection
	 */
	public static void main(String[] args) {
		
		  if (args.length != 1) { System.err.println(
		  "Usage: java connector port <port number>"); System.exit(1); }
		 

		int port = Integer.parseInt(args[0]);
		int count = 0;
		try {
			//************Server binds to given port number
			ServerSocket socket1 = new ServerSocket(port);
			System.out.println("MultiThreadedChatServer Initialized");
			while (true) {
				//*******************server listening on given port
				Socket connection = socket1.accept();
				count = count + 1;
				//******************Separate thread is maintained for every connection
				Runnable runnable = new MultiThreadChatServer(connection, ++count);
				Thread thread = new Thread(runnable);
				thread.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	MultiThreadChatServer(Socket s, int i) {
		this.connection = s;
		this.ID = i;
	}

	/*
	 * Override method run to perform following functions:
	 * Echo : Chat is echoed to every user in chat room
	 * Save : To Save user's chat
	 * Clear : To delete user's chat
	 * Get: To Get chat of a particular user
	 */
	
	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			PrintWriter out = new PrintWriter(connection.getOutputStream(), true);
			MultiThreadChatServer.userBucket.add(out);
			String inputLine = "";
			boolean locked = false;
			List<String> messages = new ArrayList<String>();
			String username = in.readLine();
			while ((inputLine = in.readLine()) != null) {
				//*******Check if user wants to save his chat
				if (inputLine.equals("SAVE")) {
					mutex.acquire();
					System.out.println("SAVE ::  Mutex aquiared by " + username);
					userHistory.put(username, messages);
					Thread.sleep(2000);
					mutex.release();
					messages = new ArrayList<>();
					System.out.println("SAVE ::  Mutex Released by " + username);
					out.println("Save Successful");
					continue;
				}//**************Check if user wants to delete his chat 
				else if (inputLine.equals("CLEAR")) {
					mutex.acquire();
					System.out.println("CLEAR ::  Mutex acquired by " + username);
					userHistory.put(username, new ArrayList<>());
					Thread.sleep(2000);
					mutex.release();
					System.out.println("CLEAR ::  Mutex Released by " + username);
					out.println("clear Successful");
					continue;
				}//*******************Check if user wants to get other user's chat 
				else if (inputLine.startsWith("GET")) {
					mutex.acquire();
					System.out.println("GET ::  Mutex acquired by " + username);
					String[] sp = inputLine.split(":");
					List<String> strings = userHistory.get(sp[1]);
					Thread.sleep(2000);
					mutex.release();
					System.out.println("GET ::  Mutex Released by " + username);
					if (strings == null || strings.isEmpty())
						out.println("No data available for the user");
					else {
						strings.forEach(out::print);
						out.println("Get Successful");
					}
					continue;
				}
				messages.add(inputLine);
				System.out.println(inputLine);
				for (PrintWriter pr : MultiThreadChatServer.userBucket) {

					pr.println(inputLine);
				}

			}

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				connection.close();
			} catch (IOException e) {
			}
		}
	}
}

