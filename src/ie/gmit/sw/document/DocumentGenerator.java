package ie.gmit.sw.document;

import java.io.*;
import java.util.*;

public class DocumentGenerator {
	
	private final Integer shingleSize;
	private final Set<Integer> hashes;
	
	public DocumentGenerator(Integer shingleSize, Set<Integer> hashes) {
		this.shingleSize = shingleSize;
		this.hashes = hashes;
	}

	public Document generate(InputStreamReader is,String title){
		return new Document(generateMinHashedShingles(generateShingles(is)),title);
	}
	
	private ArrayList<String> generateShingles(InputStreamReader is){
		ArrayList<String> shingles = new ArrayList<String>();
		try{
			BufferedReader bufferedReader = new BufferedReader(is);
			while(true){
			    String line = bufferedReader.readLine();
			    if(line == null) break;
			    
			    // Remove spaces and ASCII characters to focus solely on the words.
			    String [] words = line.split("[^a-zA-Z0-9]+");
			    int wordsSize = words.length;
			    for(int i = 0; i < wordsSize; i+=shingleSize){
			    	String shingle = String.join("", Arrays.copyOfRange(words, i, Math.min(wordsSize, i + shingleSize)));
			    	shingles.add(shingle);
			    }
			}
			bufferedReader.close();		
		}catch(Exception e){
			e.printStackTrace();
		}
		return shingles;
	}
	private Set<Integer> generateMinHashedShingles(ArrayList<String> shingles){
		Set<Integer> hashedShingles = new TreeSet<Integer>();
		//XOR the integer word values with the hashes
		for (Integer hash : hashes){
			 int min = Integer.MAX_VALUE;
			 for (String word : shingles){
				 int minHash = word.hashCode() ^ hash; //Bitwise XOR the string hashCode with the hash
				 if (minHash < min) min = minHash;
			 }
			 hashedShingles.add(min); //Only store the shingle with the minimum hash for each hash function
		}
		return hashedShingles;
	}
}
