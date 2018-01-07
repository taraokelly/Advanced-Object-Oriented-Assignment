package ie.gmit.sw.control;

public interface Control {
	
	/*
	 * This interface accommodates the command pattern, and can be re-used for other command patterns that may be add to the application.
	 */
	
	// public String exeute();
	
	// ^ Removed execute method as the return type may vary for differing control implementations.
	
	public void setCommand(Object obj);
}
