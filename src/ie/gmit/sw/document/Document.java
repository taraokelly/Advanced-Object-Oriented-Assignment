package ie.gmit.sw.document;

import java.util.Set;

public class Document {
	
	private Set<Integer> shingles;
	private String title;
	private String docId;
	
	public Document(Set<Integer> shingles, String title) {
		this.shingles = shingles;
		this.docId = title;
		this.docId = title;
	}

	public Set<Integer> getShingles() {
		return shingles;
	}

	public void setShingles(Set<Integer> shingles) {
		this.shingles = shingles;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
