package server;


import java.io.*;
import java.util.*;

//this class represent client handler for the book scrabble
public class BookScrabbleHandler implements ClientHandler{
    Scanner in;
    PrintWriter out;

    //handles query and challenge requests from user
    @Override
    public void handleClient(InputStream inFromclient, OutputStream outToClient){
        out=new PrintWriter(outToClient);
        in=new Scanner(inFromclient);
        String text = in.next();
        String[] temp = text.split(",");
        String action = temp[0];
        String[] books = new String[temp.length-1];
        System.arraycopy(temp,1,books,0,books.length);
        boolean result;
        if(action.equals("Q"))
            result = DictionaryManager.get().query(books);
        else
            result = DictionaryManager.get().challenge(books);
        out.println(result);
        out.flush();
    }

    //close streams
    @Override
    public void close() {
        in.close();
        out.close();
    }
}
