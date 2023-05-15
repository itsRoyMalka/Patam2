package dictionary;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class IOSearcher {

    public static boolean search(String word, String ... fileNames) throws FileNotFoundException {;
        for(String file : fileNames)
        {
            File text = new File(file);
            Scanner sc = null;
            sc = new Scanner(text);
            while(sc.hasNext())
            {
                if(word.equals(sc.next()))
                    return true;
            }
        }
        return false;
    }
}
