# Kafka Streams Examples
------
### Example 1 - Print messages
* Send message to input topic - `input-topic-001`
* Print using `peek`
* send to output topic - `output-topic-001`

### Execution
* Open [Example1](src/main/java/com/java/Example1.java)
* Execute method `main`
* Execute method `consumer`
* Execute method `producer`
* Producer sends message to `input-topic-001` - streaming consumes it, prints it, send to `output-topic-001` - consumer consumes from `output-topic-001`
------
### Example 2 - Convert case
* Send message input topic - `user.convert.case.input.txt`
* Print using `peek`
* convert to lower case
* send to output topic - `user.convert.case.output.txt`

### Execution
* Open [Example2](src/main/java/com/java/Example2.java)
* Execute method `main`
* Execute method `consumer`
* Execute method `producer`
------
### Example 3 - Word count
* Send message input topic - `user.word.count.input.txt`
* Print - `peek`
* convert to lower case - `mapValues`
* Divide by space - `flatMapValues`
* Use word as key - `selectKey`
* Group by key - `groupByKey`
* count - `count`
* send to output topic - `user.word.count.output.txt`

### Execution
* Open [Example3](src/main/java/com/java/Example3.java)
* Execute method `main`
* Execute method `consumer`
* Execute method `producer`
------
### Example 4 - Start starts using Topology
* Send message input topic - `user.topology.input.txt`
* Print - `peek`
* send to output topic - `user.topology.output.txt`
* Write new processor class - [Example4Processor](src/main/java/com/java/Example4Processor.java)
* Write new processor class - [Example4Processor2](src/main/java/com/java/Example4Processor2.java)
* Write new processor class - [Example4Processor3](src/main/java/com/java/Example4Processor3.java)
* Build topology
```
Topology topology = new Topology();
String sourceName = "example-4-source";
String sinkName = "example-4-sink";
topology.addSource(sourceName, INPUT_TOPIC);
topology.addProcessor("example-4-processor", Example4Processor::new, sourceName);
topology.addProcessor("example-4-processor-2", Example4Processor2::new, sourceName);
topology.addProcessor("example-4-processor-3", Example4Processor3::new, sourceName);
topology.addSink(sinkName, OUTPUT_TOPIC, new StringSerializer(), new StringSerializer(), sourceName);
```
* Build `KafkaStreams`
```
Properties properties = getStreamsProperties();
KafkaStreams kafkaStreams = new KafkaStreams(topology, properties);
kafkaStreams.start();
```

### Execution
* Open [Example4](src/main/java/com/java/Example4.java)
* Execute method `main`
* Execute method `consumer`
* Execute method `producer`