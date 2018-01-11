package ie.gmit.sw.document;

import java.util.Set;
import java.util.TreeSet;

public class JaccardHashes {
	private Set<Integer> hashes = new TreeSet<Integer>();
	private static JaccardHashes instance;
	
	private JaccardHashes(String minHashes) {
		load(minHashes);
	}
	/*
	 * Another singleton as we don't want to change the hashes as the db class just not change,
	 * and the hashes relate to the shingles saved in the db.
	 * This class loads in the pre-generated random permutations.
	 */
	
	public static synchronized JaccardHashes getInstance(String minHashes){
		if(instance == null){
            instance = new JaccardHashes(minHashes);
        }
        return instance;
    }
	
	private void load(String minHashes){
		
		String minHashesSplit [] = minHashes.split(" ");
		for(String h: minHashesSplit){
			hashes.add(Integer.parseInt(h));
		}
	}

	public Set<Integer> getHashes() {
		return hashes;
	}
}
