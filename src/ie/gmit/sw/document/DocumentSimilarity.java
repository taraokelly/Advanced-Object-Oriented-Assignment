package ie.gmit.sw.document;

public class DocumentSimilarity {
	private String title;
	private Double similarity;
	
	public DocumentSimilarity(String title, Double similarity) {
		super();
		this.title = title;
		this.similarity = similarity;
	}

	public String getTitle() {
		return title;
	}

	public Double getSimilarity() {
		return similarity;
	}
}
