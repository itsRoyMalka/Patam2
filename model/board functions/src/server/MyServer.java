package server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;


//this class represent the server on which the requests are handled
public class MyServer {
    private int port;//port of server
    private ClientHandler ch;//the type of client handler

    boolean stop;//stops the server

    //constructor
    public MyServer(int port, ClientHandler ch) {
        this.port = port;
        this.ch = ch;
    }

    //starts new thread of server
    public void start()
    {
        stop = false;
        new Thread(() -> startServer()).start();
    }

    //starts the server
    private void startServer()
    {
        try {
            ServerSocket server = new ServerSocket(port);
            server.setSoTimeout(1000);
            while(!stop){
                try {
                    Socket client = server.accept();
                    ch.handleClient(client.getInputStream(), client.getOutputStream());
                    ch.close();
                    client.close();
                }
                catch(SocketTimeoutException e){}
            }
            server.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //closes server
    public void close()
    {
        stop = true;
    }
}
