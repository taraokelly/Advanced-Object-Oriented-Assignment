package ie.gmit.sw.request;

import ie.gmit.sw.response.Responsator;

public abstract class Request implements Requestable {
	
	// Generalized request variables.
	private String taskNumber;
	
	public Request(String taskNumber) {
		this.taskNumber = taskNumber;
	}

	// Leave the implementation to the specialized concrete class.
	public abstract Responsator doRequest();
	
	// Getters and setters.
	public String getTaskNumber() {
		return taskNumber;
	}
}
