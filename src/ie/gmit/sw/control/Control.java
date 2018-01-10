package ie.gmit.sw.control;

public interface Control {
	
	/*
	 * This interface accommodates the command pattern, and can be re-used for other command patterns that may be add to the application.
	 */
	
	public Object exeute();
	
	public void setCommand(Object obj);
}
