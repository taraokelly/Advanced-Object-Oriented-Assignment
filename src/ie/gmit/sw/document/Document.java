package ie.gmit.sw.document;

public class Document {
	
	Integer [] shingles;
	String docId;
	
	public Document(Integer[] shingles, String docId) {
		this.shingles = shingles;
		this.docId = docId;
	}
}
