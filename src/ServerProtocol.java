//imports library requried
import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;

public class ServerProtocol {

	//declares and intiilizes variables needed
	public String output = "";
	public String key = "J1O6MGJ6Nd";

	//Returns a string based on what the input string was
	public String processMsg(String message) {

		//declares needed variables and initializes to get time
		Calendar calender = new GregorianCalendar();
		Date date = calender.getTime();

		//declares needed variables to format date and time from above string
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy"); 
		SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");  

		//if input message was "date", return date
		if (message.equals("date")) {
			output = dateFormat.format(date);
		}
		//else if input message was "time", return time
		else if (message.equals("time")) {
			output = timeFormat.format(date);
			
		}
		//if input message was "client closing" return "clientClosed"
		else if (message.equals("client closing")) {
			output = "clientClosed";
		}
		//if input message was "wrongMsg" return "invalidRequest"
		else if (message.equals("wrongMsg")) {
			output = "invalidRequest";
		}
		//default returns null 
		else {
			output = null;
		}
		//sends output back to server
		return output;
	}

	//returns hardcoded key
	public String getKey () {
		return key;
	}
}