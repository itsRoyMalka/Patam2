package dictionary;


import java.util.LinkedHashSet;

//this class represent least recently used policy in cache
public class LRU implements CacheReplacementPolicy {
    private LinkedHashSet<String> words;//set of words in cache

    //constructor
    public LRU() {
        this.words = new LinkedHashSet<>();
    }

    //gets set of words
    public LinkedHashSet<String> getWords() {
        return words;
    }

    //adds word to cache policy
    @Override
    public void add(String word) {
        words.remove(word);
        words.add(word);
    }

    //removes least recently used word
    @Override
    public String remove() {
        String word = null;
        if(!words.isEmpty()) {
            word = words.iterator().next();
            words.remove(word);
        }
        return word;
    }
}
