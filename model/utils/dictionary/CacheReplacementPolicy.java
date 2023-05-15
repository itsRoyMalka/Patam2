package dictionary;

//interface for any cache replacement policy
public interface CacheReplacementPolicy{
	void add(String word);//add word to cache
	String remove();//remove word from cache according to policy
}
