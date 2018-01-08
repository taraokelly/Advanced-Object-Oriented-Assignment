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
	private static Set<Integer> hashes = new TreeSet<Integer>();
	
	
	/*public void init(){
		consumer = new Thread(new Consumer(inqueue, outqueue));
		consumer.start();
	}*/
	
	public static void main(String[] args) {
		
		Integer MINHASH_LIMIT = 200;
		Integer SHINGLE_SIZE = 9;
		Random rand = new Random();
		for (int i = 0; i < MINHASH_LIMIT; i++){ 
			hashes.add(rand.nextInt());
		}
		consumer = new Thread(new Consumer(inqueue, outqueue, 200));
		consumer.start();
		
		for (int i = 0; i < 1; i++) {
			Request r = new SetupRequest("T" + i, SHINGLE_SIZE, hashes);
			inqueue.offer(r);
		}
		
		while(true){}
	}
	
}
