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
### Example 4 - Start kafka stream using Topology - Consume message from all processors
* Send message input topic - `user.topology.input.txt`
* Consume message from all processors
* Print - `peek`
* send to output topic - `user.topology.output.txt`
* Write new processor class - [Processor1](src/main/java/com/java/Processor1.java)
* Write new processor class - [Processor2](src/main/java/com/java/Processor2.java)
* Write new processor class - [Processor3](src/main/java/com/java/Processor3.java)
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

------
### Example 5 - Start kafka stream using Topology - Messages flow from processors in order
* Send message input topic - `user.topology.input.txt`
* Consumed by [Processor4](src/main/java/com/java/Processor4.java) then to [Processor5](src/main/java/com/java/Processor5.java) then to [Processor6](src/main/java/com/java/Processor6.java) then to sink topic
* Print - `peek`
* send to output topic - `user.topology.output.txt`
* Write new processor class - [Processor4](src/main/java/com/java/Processor4.java)
* Write new processor class - [Processor5](src/main/java/com/java/Processor5.java)
* Write new processor class - [Processor6](src/main/java/com/java/Processor6.java)
* Build topology
```
String sourceName = "example-5-source";
String sinkName = "example-5-sink";
String processor1 = "example-5-processor-1";
String processor2 = "example-5-processor-2";
String processor3 = "example-5-processor-3";

Topology topology = new Topology();
topology.addSource(sourceName, INPUT_TOPIC);
topology.addProcessor(processor1, () -> new Processor4(processor2), sourceName);
topology.addProcessor(processor2, () -> new Processor5(processor3), processor1);
topology.addProcessor(processor3, () -> new Processor6(sinkName), processor2);
topology.addSink(sinkName, OUTPUT_TOPIC, Serdes.String().serializer(), Serdes.String().serializer(), processor3);
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