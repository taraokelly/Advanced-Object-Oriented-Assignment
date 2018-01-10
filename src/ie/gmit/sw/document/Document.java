package ie.gmit.sw.document;

import java.util.Set;

public class Document {
	
	private Set<Integer> shingles;
	private String title;
	
	public Document(Set<Integer> shingles, String title) {
		this.shingles = shingles;
		this.title = title;
	}

	public Set<Integer> getShingles() {
		return shingles;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}