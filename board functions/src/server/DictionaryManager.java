package server;

import dictionary.Dictionary;

import java.util.HashMap;
import java.util.Map;


//this class represent a dictionary manager,that manages the requests for query and challenge and checks on all books.
public class DictionaryManager {
    private Map<String, Dictionary> books;//books of dictionary
    private static DictionaryManager manager_ref = null;//reference

    //constructor
    private DictionaryManager() {
        books = new HashMap<>();
    }

    //gets manager reference
    public static DictionaryManager get()
    {
        if(manager_ref == null)
            manager_ref = new DictionaryManager();
        return manager_ref;
    }

    //gets number of books
    public int getSize()
    {
        return books.size();
    }

    //searches for word on all given books
     public boolean query(String...args)
     {
        boolean is_exist = false;
        String s = args[args.length - 1];
        for(int i = 0 ; i< args.length - 1 ; i++)
        {
            if(!books.containsKey(args[i]))
                books.put(args[i],new Dictionary(args[i]));
        }
        for(Dictionary d : books.values())
        {
            if(d.query(s))
                is_exist = true;
        }
        return is_exist;
     }

     //activate challenge function on all given books with the given word
     public boolean challenge(String...args)
     {
         boolean is_exist = false;
         String s = args[args.length - 1];
         for(int i = 0 ; i< args.length - 1 ; i++)
         {
             if(!books.containsKey(args[i]))
                 books.put(args[i],new Dictionary(args[i]));
         }
         for(Dictionary d : books.values())
         {
             if(d.challenge(s))
                 is_exist = true;
         }
         return is_exist;
     }
}
