package ie.gmit.sw.request;

public abstract class Request implements Requestable {
	
	// Generalized request variables.
	private String taskNumber;
	
	public Request(String taskNumber) {
		this.taskNumber = taskNumber;
	}

	// Leave the implementation to the specialized concrete class.
	public abstract String doRequest();
	
	// Getters and setters.
	public String getTaskNumber() {
		return taskNumber;
	}
}
