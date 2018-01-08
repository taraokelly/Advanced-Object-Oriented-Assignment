package ie.gmit.sw.document;

import java.util.ArrayList;

public class Document {
	
	private ArrayList<Integer> shingles;
	private String title;
	private String docId;
	
	public Document(ArrayList<Integer> shingles, String title) {
		this.shingles = shingles;
		this.docId = title;
		this.docId = title;
	}

	public ArrayList<Integer> getShingles() {
		return shingles;
	}

	public void setShingles(ArrayList<Integer> shingles) {
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
