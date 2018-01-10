package ie.gmit.sw.document;

import java.util.*;

public class Jaccard {
	
	private ArrayList<DocumentSimilarity> results = new ArrayList<DocumentSimilarity>();

	public void compare(Document document, List<Document> documents) {
		final int size = document.getShingles().size();
		for(Document doc: documents){
			final int s = doc.getShingles().size();
		    doc.getShingles().retainAll(document.getShingles());
		    final int intersection = doc.getShingles().size();
		    results.add(new DocumentSimilarity(doc.getTitle(), 1d / (s + size - intersection) * intersection));
		}
	}

	public ArrayList<DocumentSimilarity> getResults() {
		return results;
	}

	public void clearResults() {
		this.results = new ArrayList<DocumentSimilarity>();
	}
	
}
