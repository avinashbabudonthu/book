# Kafka Streams Examples
------
### Problem
* Take message from one topic (input topic)
* Print using `peek`
* send to another topic (output topic)

### Solution
* open class - [Example0001](src/main/java/com/java/Example0001.java)
* Run main method
* Run `consumer` test case
* Run `producer` test case
* Producer sends message to `input-topic-001` - streaming consumes it, prints it, send to `output-topic-001` - consumer consumes from `output-topic-001`
------
### Problem
* Take message from one topic (input topic)
* Print using `peek`
* convert to lower case
* send to another topic (output topic)

### Solution
* Refer - [Example0002](src/main/java/com/java/Example0002.java)