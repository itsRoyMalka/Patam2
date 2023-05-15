package dictionary;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


//this class represent the dictionary of the game
public class Dictionary {


    private CacheManager existingWords;//all words in dictionary
    private CacheManager nonExistingWords;//all words not found in dictionary
    private BloomFilter bf;//bloom filter of dictionary

    private String[] files;//all files of words

    //constructor
    public Dictionary(String ... fileNames)
    {
        existingWords = new CacheManager(400, new LRU());
        nonExistingWords = new CacheManager(100,new LFU());
        bf = new BloomFilter(256,"MD5","SHA1");
        this.files = new String[fileNames.length];
        for(int i = 0 ; i < fileNames.length ; i++)
            files[i] = new String(fileNames[i]);
        for(String file : fileNames) {
            File text = new File(file);
            Scanner sc;
            try {
                sc = new Scanner(text);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            while (sc.hasNext()) {
                String s = sc.next();
                existingWords.add(s);
                bf.add(s);
            }
        }
    }

    //checks if word is in dictionary
    public boolean query(String s)
    {
        if(existingWords.query(s))
            return true;
        if(nonExistingWords.query(s))
            return false;
        if(bf.contains(s))
        {
            existingWords.add(s);
            return true;
        }
        nonExistingWords.add(s);
        return false;
    }

    //if the query returned false and the user insists the word exist then does thorough check for word
    public boolean challenge(String s)
    {
        try {
            if (IOSearcher.search(s, files)) {

                existingWords.add(s);
                return true;
            }
            nonExistingWords.add(s);
            return false;
        }catch (FileNotFoundException e){
            return false;
        }
    }



}
