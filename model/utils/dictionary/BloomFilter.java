package dictionary;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;

//this class represent bloom filter,which help to dictate if a word is in the dictionary
public class BloomFilter {
	private BitSet bs;
	private MessageDigest[] md;

	//constructor
	public BloomFilter(int bit_size, String...hash)
	{
		bs = new BitSet(bit_size);
		md = new MessageDigest[hash.length];
		for(int i = 0 ; i < hash.length ; i++) {
			try {
				md[i] = MessageDigest.getInstance(hash[i]);
			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException(e);
			}
		}
	}

	//gets place in bitset to put string
	public int getValue(MessageDigest md,String s)
	{
			byte[] bts = md.digest(s.getBytes());
			BigInteger bi = new BigInteger(bts);
			int value = bi.intValue();
			if(value < 0)
				value = -value;
			return value % bs.size();
	}

	//add string to the bitset
	public void add(String s){

		for (MessageDigest m : md) {
			bs.set(getValue(m,s));
		}
	}

	//checks if bitset contains string
	public boolean contains(String s){
		for(MessageDigest m : md){
			if(!bs.get(getValue(m,s)))
				return false;
		}
		return true;
	}

	@Override
	public String toString() {
		String s = new String() ;
		int val;

		for(int i = 0 ; i<bs.length();i++)
		{
			s += bs.get(i) ? "1" : "0";

		}
		return s;
	}
}
