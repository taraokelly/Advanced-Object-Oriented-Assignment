package ie.gmit.sw.control;

public interface Control {
	
	/*
	 * This interface accommodates the command pattern, and can be used in any command pattern in the application.
	 */
	
	public String exeute();
	public void setCommand(Object obj);
}
