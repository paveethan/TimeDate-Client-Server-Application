//Imports required libraries
import java.io.*;
import java.net.*;

public class serverThread extends Thread {
	
	//declares and initializes needed variables
	Socket a1clientSocket;
	static boolean closed = false;

	//constructor to accept socket 
	public serverThread(Socket socket) {
		super("serverThread");
		a1clientSocket = socket;
	}

	//runnable code upon a new instance of this object
	public void run () {

		//declares and initializes needed variables
		PrintWriter out;
		BufferedReader in;
		String key, inLine, outLine;
		ServerProtocol a1Protocol;

		//try portion to communicate with client via serverProtocol
		try {
			//sets autoflush to true, and makes out utilie acClientSocket's output stream
			out = new PrintWriter (a1clientSocket.getOutputStream(), true);
			//sets in to use a1clientSocket's inputu stream
			in = new BufferedReader(new InputStreamReader(a1clientSocket.getInputStream()));

			//initializes a1 protocol
			a1Protocol = new ServerProtocol();
			//gets key from a1Protocol
			key = a1Protocol.getKey();
			//prints key to socket (to act as handshake)
			out.println(key);

			//while there is a non-null message from the in (the socket's input-steam) run the following
			while ((inLine = in.readLine()) != null) {
				//sends message that was read to a1Protocol and stores response
				outLine = a1Protocol.processMsg(inLine);
				//if the message is "clientClosed"
				if (outLine.equals("clientClosed")) {
					//set closed to true
					closed = true;
				}
				//else client had not closed so print out message via 'out' (socket's output-stream)
 				else {
					out.println(outLine);
				}
			}

		} 
		//deals with IOExceptions
		catch (IOException e) {
			//if closed then no need to display error message since we were expecting it
			if (closed) {
			}
			//else prints error messages to screen
			else {
				System.err.println ("Error! Exception caught while trying to listen for a connection or while trying to listen on the specified port number.");
				System.err.println("Exception Message: "+e.getMessage());
			}
		}

	}
}