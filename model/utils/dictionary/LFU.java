package dictionary;


import java.util.*;

//this class represent least frequently used policy
public class LFU implements CacheReplacementPolicy {

    private HashMap<String ,Integer > words;//words and number of times used
    private TreeMap<Integer, LinkedHashSet<String>> word_tree;//connect between the number of times used to all words of that number

    //constructor
    public LFU() {
        words = new HashMap<>();
        word_tree = new TreeMap<>();
    }

    //gets hash map of words and counter
    public HashMap<String, Integer> getWords() {
        return words;
    }

    //gets tree map of counter and array of words
    public TreeMap<Integer, LinkedHashSet<String>> getWordTree() {
        return word_tree;
    }

    //adds word to cache policy
    @Override
    public void add(String word) {
        if(!words.containsKey(word))
        {
            words.put(word,1);
            if(!word_tree.containsKey(1))
                word_tree.put(1,new LinkedHashSet<String>());
            word_tree.get(1).add(word);
        }
        else {
            int counter = words.get(word);
            words.remove(word);
            word_tree.get(counter).remove(word);
            if (word_tree.get(counter).size() == 0)
                word_tree.remove(counter);
            words.put(word, counter + 1);
            if (!word_tree.containsKey(counter + 1))
                word_tree.put(counter + 1, new LinkedHashSet<String>());
            word_tree.get(counter + 1).add(word);
        }
    }

    //removes least frequently used
    @Override
    public String remove() {
        int min_counter = word_tree.firstKey();
        String lfu_word = word_tree.get(min_counter).iterator().next();
        word_tree.get(min_counter).remove(lfu_word);
        if(word_tree.get(min_counter).size() == 0)
            word_tree.remove(min_counter);
        return lfu_word;
    }
}
