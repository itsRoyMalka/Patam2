package server;

import java.io.InputStream;
import java.io.OutputStream;

//this class represent a generic client handler
public interface ClientHandler {

	//handles request from client
	void handleClient(InputStream inFromclient, OutputStream outToClient);

	//closes streams
	void close();
}
