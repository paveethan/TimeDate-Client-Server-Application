import java.net.*;
import java.io.*;

public class server {
	public static void main (String[] args) throws IOException {

	//declares and initalizes needed variables
	int portNumber = 0;
	boolean listeningY = true;
	//Checks to see if a argument of 1 is present
		if (args.length  != 1) {
			System.err.println ("Wrong arguments! Use as 'java server <port number>',"
				+"where <port number> is a valid integer over 1024!");
			System.exit(1);
		}

		//converts argumentn to number and stores it as portNumber
		portNumber = Integer.parseInt(args[0]);

		//error checking to see if portNumber is in accepted range
		if (portNumber < 1025) {
			System.err.println ("Invalid Argument: Please enter a port number greater than 1025, less then 49151");
			System.exit(1);
		}
		else if (portNumber > 49151) {
			System.err.println ("Invalid Argument: Please enter a port number greater than 1025, less then 49151");
			System.exit(1);
		}

		//started up fine, so print to screen that server ris running
		System.out.println("Server Running...");

		//try portion to create server socket
		try (
			ServerSocket a1Socket = new ServerSocket(portNumber);
			) {
				while (listeningY) {
					//creates a new instance of serverThread whenever it accepts a client socket request
					new serverThread(a1Socket.accept()).start();
				}
		}
		//catches and deals with IOExceptions
		catch (IOException e) {
			//prints out error messages 
			System.err.println ("Error! Exception caught (server side) while trying to listen for a connection or while trying to listen on the specified port number.");
			System.err.println("Exception Message: "+e.getMessage());
		}
	}
}