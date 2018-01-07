package ie.gmit.sw;

import java.util.*;

import ie.gmit.sw.request.*;
import ie.gmit.sw.request.consumer.Consumer;

public class ServiceHandler {
	//An Asynchronous Message Facade
	private static Queue<Requestable> inqueue = new LinkedList<Requestable>();
	private static Map<String, String> outqueue = new LinkedHashMap<String, String>();
	//Start Consumer thread & pass in queues.
	private static Thread consumer;
	
	/*public void init(){
		consumer = new Thread(new Consumer(inqueue, outqueue));
		consumer.start();
	}*/
	
	public static void main(String[] args) {
		
		consumer = new Thread(new Consumer(inqueue, outqueue));
		consumer.start();
		
		for (int i = 0; i < 1; i++) {
			Request r = new SetupRequest("T" + i, 200, 9);
			inqueue.offer(r);
		}
		
		while(true){}
	}
	
}
