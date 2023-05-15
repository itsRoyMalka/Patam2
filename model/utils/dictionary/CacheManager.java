package dictionary;


import java.util.HashSet;

//this class represent a cache manager to contain words of dictionary
public class CacheManager {
    private int size;//size of cache
    private CacheReplacementPolicy crp;//cache policy
    private HashSet<String> cache_words;//words in cache

    //constructor
    public CacheManager(int size, CacheReplacementPolicy crp) {
        this.size = size;
        this.crp = crp;
        cache_words = new HashSet<String>();
    }

    //gets words of cache
    public HashSet<String> getWords() {
        return cache_words;
    }

    //gets size of cache
    public int getSize() {
        return size;
    }

    //sets size of cache
    public void setSize(int size) {
        this.size = size;
    }

    //gets policy of cache
    public CacheReplacementPolicy getCrp() {
        return crp;
    }

    //sets policy of cache
    public void setCrp(CacheReplacementPolicy crp) {
        this.crp = crp;
    }

    //checks if word is in cache
    public boolean query(String w)
    {
        if(cache_words == null)
            return false;
        return cache_words.contains(w);
    }

    //adds word to cache
    public void add(String w)
    {
        cache_words.add(w);
        crp.add(w);
        if(cache_words.size() > this.size)
            cache_words.remove(crp.remove());
    }
}
