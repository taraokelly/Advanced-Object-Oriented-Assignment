package ie.gmit.sw.response;

import java.util.ArrayList;

import ie.gmit.sw.document.DocumentSimilarity;

public class ComparisonResponse extends Response {
	
	private ArrayList<DocumentSimilarity> results;
	
	public ComparisonResponse(String resultStatus, ArrayList<DocumentSimilarity> results) {
		super(resultStatus);
		this.results = results;
	}
	
	public ArrayList<DocumentSimilarity> getResults() {
		return results;
	}

}
