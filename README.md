# Advanced Object Oriented Assignment
A Java web application, designed with object oriented principles and patterns in mind, that enables two or more text documents to be compared for similarity.

+ [Initial Plan](#initial-plan)
+ [Additional OO Features](#additional-oo-features)
+ [UML Diagram](#uml-diagram)
+ [Issues](#issues)
+ [How to Run](#how-to-run)
+ [References](#references)

## Initial Plan

- [x] Message facade to hold requests and responses.
- [x] Consumer thread launches message facade requests from a thread pool.
- [x] Requests (to add & set up) - chain of responsibility or **command**.
- [ ] Proxy db handler.

## Additional OO Features

- Database Singleton - as we don't want threads to have different instances of the db. Then concurrent access would not be controlled.
- JaccardHashes Singleton - as we don't want the hashes to be able to change, corrupting the integrity of the minhashed shingles being add to the database and used for the jaccard comparison.

## UML Diagram

![alt text](https://github.com/taraokelly/Advanced-Object-Oriented-Assignment/blob/master/UML.PNG "UML Diagram")

## Issues

### Deploying to Tomcat:

I had been working with eclipse while developing the non-serlvet classes. When I deployed them to tomcat I had trouble creating the database. I then began using a Dynamic Web Application in Eclipse to get it working, however this Dynamic Web Application required the external jars to be in the application library. Once I had the database working in Eclipse, I transfered the classes back over to the original project. 

I then ran into another issue with accessing the resources package. This is important because I have two Recquestable objects that are used in the command pattern - one to compare and one to populate the database. I have commented out and left the implementation in to demonstrate how the command pattern works and how a new object of type requestable could be used in the command object.

I was also reading the random permutations from a text file in the resources directory. The permutations are now being read from the web.xml.

## How to Run

- Copy the jaccard file or the jaccard.war into your Tomcat/webapps folder. **N.B. only one!**.
- Create a directory called **G00322214** in your user Documents directory.
- Make sure the db4o v8 jars are in your Tomcat/lib folder.
- Start up tomcat with the startup.bat in your Tomcat/bin folder.

## References

https://stackoverflow.com/questions/19946277/how-to-pass-a-string-value-from-one-servlet-to-another-servlet
https://stackoverflow.com/questions/219585/including-all-the-jars-in-a-directory-within-the-java-classpath
http://matthewcasperson.blogspot.ie/2013/11/minhash-for-dummies.html
http://infolab.stanford.edu/~ullman/mmds/ch3.pdf
https://www.youtube.com/watch?v=96WOGPUgMfw&feature=youtu.be
https://coderanch.com/t/496550/java/package-exist-error
https://www.journaldev.com/1617/chain-of-responsibility-design-pattern-in-java
https://stackoverflow.com/questions/2657971/how-to-tell-if-there-is-an-available-thread-in-a-thread-pool-in-java
https://dzone.com/articles/design-patterns-command
https://docs.oracle.com/cd/E13222_01/wls/docs90/plugins/http_proxy.html
http://www.servletsuite.com/servlets/httpproxy.htm
https://docs.oracle.com/cd/E13222_01/wls/docs61/adminguide/http_proxy.html
https://stackoverflow.com/questions/2657971/how-to-tell-if-there-is-an-available-thread-in-a-thread-pool-in-java
https://stackoverflow.com/questions/12771500/best-way-of-creating-and-using-an-anonymous-runnable-class
http://www.logicbig.com/how-to/java/list-all-files-in-resouce-folder/
https://stackoverflow.com/questions/11332772/java-string-split-on-all-non-alphanumeric-except-apostrophes
https://stackoverflow.com/questions/1978933/a-quick-and-easy-way-to-join-array-elements-with-a-separator-the-opposite-of-sp
https://stackoverflow.com/questions/11001720/get-only-part-of-an-array-in-java
https://stackoverflow.com/questions/43634867/computing-jaccard-similarity-in-java
https://codereview.stackexchange.com/questions/67274/error-handling-in-servlets-and-jsps

-----

__*Tara O'Kelly - G00322214@gmit.ie*__