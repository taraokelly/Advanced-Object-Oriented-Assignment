package ie.gmit.sw.document.db;

import java.io.File;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.constraints.UniqueFieldValueConstraint;
import com.db4o.ta.TransparentActivationSupport;
import com.db4o.ta.TransparentPersistenceSupport;

import ie.gmit.sw.document.Document;
import xtea_db4o.XTEA;
import xtea_db4o.XTeaEncryptionStorage;

public class DatabaseHandler {
	private ObjectContainer db = null;
	private static ReentrantLock lock = new ReentrantLock();
	private static DatabaseHandler instance;
	
	/*
	 * Thread Safe Singleton to manage the database.
	 */
	
	public static synchronized DatabaseHandler getInstance(String path){
        if(instance == null){
            instance = new DatabaseHandler(path);
        }
        return instance;
    }
	
	private DatabaseHandler(String dbPath){
		EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
		config.common().add(new TransparentActivationSupport()); 
		config.common().add(new TransparentPersistenceSupport());
		config.common().updateDepth(7); //Propagate updates
		config.common().objectClass(Document.class).objectField("title").indexed(true);
		config.common().add(new UniqueFieldValueConstraint(Document.class, "title")); 
		
		//Use the XTea lib for encryption. The basic Db4O container only has a Caesar cypher... Dicas quod non est ita!
		config.file().storage(new XTeaEncryptionStorage("password", XTEA.ITERATIONS64));
		
		String path = ((System.getProperty("user.home") + File.separatorChar + "Documents/" + dbPath).replace("\\", "/"));
		//Open a local database. Use Db4o.openServer(config, server, port) for full client / server
		db = Db4oEmbedded.openFile(path);
	}
	public void addAllDocuments(List<Document> documents){
		lock.lock();
		try{
			for (Document d: documents){
				//Adds the doc to the database
				ObjectSet<Document> result = db.queryByExample(d);
		        if(!result.hasNext()){
		        	db.store(d);
		        } 
			}
			//Commits the transaction
			db.commit(); 
		}
		catch(Exception e){
			//Rolls back the transaction
			db.rollback();
		}finally{
			lock.unlock();
		}
	}
	
	public void addDocument(Document document){
		lock.lock();
		try{
			//Adds the doc to the database
			ObjectSet<Document> result = db.queryByExample(document);
	        if(result.isEmpty()){
	        	db.store(document);
	        	//Commits the transaction
				db.commit(); 
	        } 
		}catch(Exception e){
			db.rollback();
		}finally{
			lock.unlock();
		}
	}
	
	public ObjectSet<Document> getAllDocuments(){
		ObjectSet<Document> documents;
		lock.lock();
		try{
			//An ObjectSet is a specialised List for storing results
			documents = db.query(Document.class);
		}finally{
			lock.unlock();
		}
		return documents;
	}
}
