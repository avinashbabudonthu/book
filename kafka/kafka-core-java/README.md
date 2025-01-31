# Kafka Core Java: A Simple Kafka Producer and Consumer Example

This project demonstrates a basic implementation of Apache Kafka producers and consumers using Java. It provides a foundation for building Kafka-based applications, showcasing how to send and receive messages from Kafka topics.

The project includes a simple producer that sends messages to a Kafka topic and a consumer that reads messages from the same topic. It's designed to help developers understand the basics of Kafka integration in Java applications and serve as a starting point for more complex Kafka-based systems.

## Repository Structure

```
.
├── pom.xml
├── README.md
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── jms
    │   │           └── App.java
    │   └── resources
    │       └── log4j.properties
    └── test
        └── java
            └── com
                ├── consumer
                │   └── Consumer.java
                └── producer
                    └── Producer.java
```

- [`pom.xml`](pom.xml): Maven project configuration file
- [`src/main/java/com/jms/App.java`](src/main/java/com/jms/App.java): Main application entry point
- [`src/test/java/com/consumer/Consumer.java`](src/test/java/com/consumer/Consumer.java): Kafka consumer implementation
- [`src/test/java/com/producer/Producer.java`](src/test/java/com/producer/Producer.java): Kafka producer implementation
- `src/main/resources/log4j.properties`: Logging configuration file

## Usage Instructions

### Installation

Prerequisites:
- Java 8 or higher
- Maven 3.5 or higher
- Apache Kafka 0.10.0.1 or compatible version

To install the project, follow these steps:

1. Clone the repository:
   ```
   git clone <repository-url>
   cd kafka-core-java
   ```

2. Build the project using Maven:
   ```
   mvn clean install
   ```

### Getting Started

1. Ensure your Kafka broker is running on `localhost:9092`. If it's running on a different address, update the `bootstrap.servers` property in both `Consumer.java` and `Producer.java`.

2. Run the producer to send a message:
   ```
   mvn test -Dtest=Producer#sendMessageWithoutKey
   ```

3. Run the consumer to receive messages:
   ```
   mvn test -Dtest=Consumer#consumeByTopicBySubcribe
   ```

### Configuration Options

The Kafka producer and consumer can be configured by modifying the `Properties` object in their respective classes:

- `bootstrap.servers`: Kafka broker address (default: `localhost:9092`)
- `key.serializer` and `value.serializer`: Message serialization classes
- `key.deserializer` and `value.deserializer`: Message deserialization classes
- `group.id`: Consumer group ID (for the consumer only)

### Common Use Cases

1. Sending a message to Kafka:

```java
Properties properties = new Properties();
properties.put("bootstrap.servers", "localhost:9092");
properties.put("key.serializer", StringSerializer.class.getName());
properties.put("value.serializer", StringSerializer.class.getName());

try (KafkaProducer<String, String> producer = new KafkaProducer<>(properties)) {
    ProducerRecord<String, String> record = new ProducerRecord<>("topic-1", "Hello, Kafka!");
    producer.send(record);
}
```

2. Consuming messages from Kafka:

```java
Properties properties = new Properties();
properties.put("bootstrap.servers", "localhost:9092");
properties.put("key.deserializer", StringDeserializer.class.getName());
properties.put("value.deserializer", StringDeserializer.class.getName());
properties.put("group.id", "my-consumer-group");

try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties)) {
    consumer.subscribe(Arrays.asList("topic-1"));
    while (true) {
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
        for (ConsumerRecord<String, String> record : records) {
            System.out.println("Received message: " + record.value());
        }
    }
}
```

### Testing & Quality

The project uses JUnit for testing. To run all tests:

```
mvn test
```

### Troubleshooting

1. Connection refused error:
   - Problem: `java.net.ConnectException: Connection refused`
   - Solution: Ensure that the Kafka broker is running and accessible at the specified address.
   - Debug: Check the Kafka server logs and verify the `bootstrap.servers` property in your code.

2. Topic not found:
   - Problem: `org.apache.kafka.common.errors.UnknownTopicOrPartitionException: This server does not host this topic-partition.`
   - Solution: Create the topic before producing or consuming messages.
   - Debug: Use Kafka's command-line tools to list and create topics:
     ```
     kafka-topics.sh --list --bootstrap-server localhost:9092
     kafka-topics.sh --create --topic topic-1 --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
     ```

3. Serialization errors:
   - Problem: `org.apache.kafka.common.errors.SerializationException: Can't convert value of class X to class Y`
   - Solution: Ensure that the serializer and deserializer classes match the data types you're sending and receiving.
   - Debug: Double-check the `key.serializer`, `value.serializer`, `key.deserializer`, and `value.deserializer` properties in your code.

To enable debug logging, add the following line to your `log4j.properties` file:

```
log4j.logger.org.apache.kafka=DEBUG
```

Log files are typically located in the `target/surefire-reports` directory after running tests with Maven.

## Data Flow

The data flow in this Kafka application follows these steps:

1. Producer sends a message to the Kafka topic "topic-1".
2. Kafka broker receives the message and stores it.
3. Consumer subscribes to "topic-1" and polls for new messages.
4. Kafka broker sends the stored messages to the consumer.
5. Consumer processes the received messages.

```
[Producer] -> (topic-1) -> [Kafka Broker] -> (topic-1) -> [Consumer]
```

Note: The producer and consumer operate independently. The consumer will receive messages that were sent before it started, as long as they are still available in the Kafka topic.