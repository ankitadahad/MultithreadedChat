
#Name : Click Chat 
---------------------------------------------------------------------------------------------------------------------
 Simple Multithreaded Chatting application (Server and Clinet)


##Modules :
---------------------------------------------------------------------------------------------------------------------

1. MultiThreaded Chat Server
2. Chat Client

---------------------------------------------------------------------------------------------------------------------
##Programming Language : Java
---------------------------------------------------------------------------------------------------------------------

###Insturctions to Run Server :
1. Setup Java environment variables for java and build and compile the project from command prompt.
2. Use javac <filename>.java command to compile the server, MultiThreadChatServer.java
3. Run the java file and provide port number that server listens on.
	 java <filename> <portnumber>
4. You can see the message : MultiThreadedChatServer Initialized
   Thus, server is up and ready.

--------------------------------------------------------------------------------------------------------------------
###Instructions to Run Client :
1. Use javac<filename>.java command to compile the client, ChatClient.java
2. Run the java file 
	java <filename> 
3. Enter the IPAddress, Port Number and username in the dialog box that appears in specified format
	eg : 127.0.0.1:10001:Ankita
4.Start typing message in text box that appears
5. Use "Send Massage" button to send messages
6. Use "Save" button to save whatever you have typed
7. Use "Clear" button to clear text area.
8. Type a username and use "Get" button to get that user's chat history
9. Use "Delete History" button to delete your chat history.

You can run 2 or more instances of client to see the chatting between clients.
--------------------------------------------------------------------------------------------------------------------

##Features

###How it works: 

1. Once 2 or more instances of client are running, type "Hello" in console of client1
	You can see the output as "Username1 :  Hello    At <timestamp>" 
	in console windows of both the clients.
2. Now , type "Hi" in console of client2
	You can see "Username2 :  Hi    At <timestamp>"
	in console windows of both the clients.
3. Timestamp specifies the time when message was sent.
4. Type exit to end the chat.
5. When multiple uesrs try to save, get or delete at the same time, only one user is allowed at a time and events happen 	in mutually exclusive manner.

