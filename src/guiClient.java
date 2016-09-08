//Standard library
import java.io.*;
//Library to work with sockets
import java.net.*;
//libraries to work to implement GUI
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class guiClient implements ActionListener {

	//Declares needed  variables
	JFrame frame;
	TextField textField;
	JButton getTime, getDate, wrongMsg;
	JPanel mainPanel, buttonPanel;
	String serverMsg;
	ButtonGroup group;

	//Declares public static needed variables
	public static String hostName;
	public static int portNumber;

	//Declares public variables
	public Socket a1Socket;
	public PrintWriter out;
	public BufferedReader in;
	
	//Declares and initializes needed variables
	String key = "J1O6MGJ6Nd";
	Boolean handShake = false;

	//main method
	public static void main (String[] args) {
		
		//Error checking for arguments
		if (args.length != 2) {
			System.err.println ("Wrong arguments! Use as 'java guiClient <port number> <hostName> ',"
				+"where <port number> is a valid integer over 1024!");
			System.exit(1);
		}

		//gets host name and stores it as hostName
		hostName = args[1];

		//converts argumentn to number and stores it as portNumber
		portNumber = Integer.parseInt(args[0]);

		//error checking to see if portNumber is in accepted range
		if (portNumber < 1025) {
			System.err.println ("Invalid Argument: Please enter a port number greater than 1025, less then 65535 ");
			System.exit(1);	
	}
		else if (portNumber > 65535) {
			System.err.println ("Invalid Argument: Please enter a port number greater than 1025, less then 65535 ");
			System.exit(1);
		}
		//starts up the GUI
		new guiClient();
	}

	public guiClient() {

		//Initializes frame
		frame = new JFrame();
		//sets frame to use the FlowLayout Manager
		frame.setLayout(new BorderLayout());

		mainPanel = new JPanel();

		//initaiizes textfield and aspects 
		textField = new TextField(25);
		textField.setEditable(false);

		buttonPanel = new JPanel(new GridLayout(3,1));

		//initalizes the "Get Date" button
		getDate = new JButton("Get Date");
		getDate.setActionCommand("getDate");
		getDate.addActionListener(this);

		//initalizes the "Get Time" button
		getTime = new JButton("Get Time");
		getTime.setActionCommand("getTime");
		getTime.addActionListener(this);

		//initalizes the "Send Invalid Reuquest" Button
		wrongMsg = new JButton ("Send Invalid Request");
		wrongMsg.setActionCommand("wrongMsg");
		wrongMsg.addActionListener(this);

		group = new ButtonGroup();
		group.add(getDate);
		group.add(getTime);
		group.add(wrongMsg);

		//adds textfield to mainPanel
		mainPanel.add(textField);
		
		//adds buttons to buttonPanel
		buttonPanel.add(getDate);
		buttonPanel.add(getTime);
		buttonPanel.add(wrongMsg);

		//adds mainPanel to frame and sets it as center
		frame.add(mainPanel, BorderLayout.CENTER);
		//adds buttonPanel to frame and sets it as south
		frame.add(buttonPanel, BorderLayout.SOUTH);
		//sets the frame size to 450 by 300
		frame.setSize (450, 300);

		//deals with the fram closing
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt){
				//closes socket properly
				if (handShake) {
					closeConnection();
				}
				System.exit(0);
			}
		});

		//sets frame to visible
		frame.setVisible(true);
		//calls the void startConnection method to initiate the socket
		startConnection();
	}

	//deals with the socket stuff
	public void startConnection() {
		//try portion
		try  {
				//declares and initilizes needed stuff for socket (socket, printwriter out, and bufferedreader in)
				a1Socket = new Socket(hostName, portNumber);
				out = new PrintWriter(a1Socket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(a1Socket.getInputStream()));

				//keeps reading in messages from socket's inputstream, and only continues if the message is not null
				while ((serverMsg = in.readLine()) != null) {
					//if the message is anything other then "null" continue
					if (!(serverMsg.equals(null)))
					{
						//if the message is equal to key then set handShake to true (acts as a handshake to verify connection)
						if (serverMsg.equals(key)) {
							out.println("got key");
						}
						//or else its another message
						else {
							//checks to see if handShake is true (the connection is verified)
							if (handShake) {
								//checks to see if server sent "invalidRequest" thus displaying a message dialog
								if (serverMsg.equals("invalidRequest")) {
									JOptionPane.showMessageDialog (null, "Invalid request sent");
								}
								//else message sent a valid request
								else {
									//sets the incoming message as textField
									textField.setText(serverMsg);
								}
							}
						}
					}
				}
		}
		//catch if it could not connect to host
		catch (UnknownHostException e) {
			System.err.println("Could not resolve host: "+hostName);
			System.exit(1);
		}
		//catch if something went wrong with the connection
		catch (IOException e) {
			System.err.println("Coudln't get I/O connection to "+hostName);
			System.exit(1);

		}
	}


	//Deals with button clicks
	public void actionPerformed(ActionEvent e) {
		//if action command is "getTime" then send "time" to socket via printWriter out
		if (e.getActionCommand ().equals ("getTime")) {
			out.println("time");
		}
		//if action command is "getDate" then send "date" to socket via printWriter out
		if (e.getActionCommand ().equals ("getDate")) {
			out.println("date");
		}
		//if action command is "wrongMsg" then send "wrongMsg" to socket via printWriter out
		if (e.getActionCommand ().equals ("wrongMsg")) {
			out.println("wrongMsg");
		}
	}

	//deals with the client exiting
	public void closeConnection() {
		//sends the message "client closing" to the server socket via PrintWriter out
		out.println("client closing");
	}
}