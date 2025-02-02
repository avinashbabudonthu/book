# Kafka Core Java: A Comprehensive Kafka Testing Suite

## Source Files

- [src/main/java/com/kafka/TopicTest.java](src/main/java/com/kafka/TopicTest.java): Contains test methods for creating and deleting Kafka topics using the Kafka Admin Client API.
- [src/main/java/com/kafka/ProducerTest.java](src/main/java/com/kafka/ProducerTest.java): Demonstrates Kafka producer functionality, including sending messages with and without keys.
- [src/main/java/com/kafka/ConsumerTest.java](src/main/java/com/kafka/ConsumerTest.java): Shows how to set up and use a Kafka consumer to consume messages from a Kafka topic.
- [pom.xml](pom.xml): Maven project configuration file specifying dependencies and build settings.
- [Dockerfile](Dockerfile): Defines the Docker image for the application using OpenJDK 17.

## Project Explanation

This project provides a comprehensive suite of Kafka testing utilities implemented in Java. It includes functionality for creating and deleting Kafka topics, producing messages with and without keys, and consuming messages from Kafka topics.

The Kafka Core Java project is designed to help developers test and validate their Kafka-based applications. It offers a set of test classes that demonstrate how to interact with Kafka using the Kafka Admin Client API and Kafka Producer/Consumer APIs. These tests can serve as a foundation for building more complex Kafka applications or as a learning tool for those new to Kafka development in Java.

Key features of this project include:
- Topic management (creation and deletion)
- Message production with and without keys
- Message consumption with subscription
- Dockerized environment for easy setup and deployment

## Repository Structure

```
.
├── Dockerfile
├── pom.xml
├── README.md
└── src
    └── main
        ├── java
        │   └── com
        │       └── kafka
        │           ├── ConsumerTest.java
        │           ├── Main.java
        │           ├── ProducerTest.java
        │           └── TopicTest.java
        └── resources
            └── log4j.xml
```

Key Files:
- `pom.xml`: Maven project configuration file
- `Dockerfile`: Defines the Docker image for the application
- `src/main/java/com/kafka/Main.java`: Entry point of the application
- `src/main/java/com/kafka/TopicTest.java`: Tests for Kafka topic creation and deletion
- `src/main/java/com/kafka/ProducerTest.java`: Tests for Kafka message production
- `src/main/java/com/kafka/ConsumerTest.java`: Tests for Kafka message consumption

## Usage Instructions

### Installation

Prerequisites:
- Java Development Kit (JDK) 21
- Apache Maven 3.6+
- Docker (optional, for containerized deployment)

To install the project:

1. Clone the repository:
   ```
   git clone <repository-url>
   cd kafka-core-java
   ```

2. Build the project:
   ```
   mvn clean package
   ```

### Running the Tests

To run the Kafka tests, ensure you have a Kafka broker running on `localhost:29092`. You can then execute the tests using Maven:

```
mvn test
```

### Topic Management

The `TopicTest` class demonstrates how to create and delete Kafka topics:

```java
TopicTest topicTest = new TopicTest();
topicTest.createTopic(); // Creates a topic named "topic-2"
topicTest.deleteTopic(); // Deletes the "topic-2" topic
```

### Producing Messages

The `ProducerTest` class shows two ways to produce messages:

1. Without a key:
```java
ProducerTest producerTest = new ProducerTest();
producerTest.sendMessageWithoutKey();
```

2. With a key:
```java
ProducerTest producerTest = new ProducerTest();
producerTest.sendMessageWithKey();
```

### Consuming Messages

The `ConsumerTest` class demonstrates how to consume messages from a Kafka topic:

```java
ConsumerTest consumerTest = new ConsumerTest();
consumerTest.consumeWithSubscription();
```

### Troubleshooting

Common issues and solutions:

1. Connection refused error:
   - Problem: Unable to connect to Kafka broker
   - Error message: "Connection to node -1 (localhost/127.0.0.1:29092) could not be established."
   - Solution: Ensure Kafka is running and accessible at localhost:29092. Check firewall settings and Kafka server logs.

2. Topic not found:
   - Problem: Attempting to produce or consume from a non-existent topic
   - Error message: "Topic topic-1 not present in metadata after 60000 ms."
   - Solution: Use the `TopicTest.createTopic()` method to create the topic before producing or consuming messages.

Debugging:
- Enable debug logging by modifying `src/main/resources/log4j.xml`:
  ```xml
  <logger name="org.apache.kafka">
    <level value="DEBUG"/>
  </logger>
  ```
- Kafka client logs will be available in the console output.

Performance optimization:
- Monitor producer and consumer throughput using Kafka's built-in metrics.
- Use the `kafka-consumer-groups` command-line tool to check consumer lag.
- Adjust batch size and linger.ms settings for producers to optimize throughput.

## Data Flow

The Kafka Core Java project demonstrates a simple data flow through a Kafka ecosystem:

1. Topic Creation: The application starts by creating a Kafka topic using the Admin Client API.
2. Message Production: The producer sends messages to the created topic.
3. Message Consumption: The consumer subscribes to the topic and retrieves messages.
4. Topic Deletion: Finally, the topic can be deleted using the Admin Client API.

```
[Topic Creation] -> [Message Production] -> [Kafka Broker] -> [Message Consumption] -> [Topic Deletion]
```

Note: Ensure that the Kafka broker is properly configured and running before executing the tests.

## Deployment

To deploy the application using Docker:

1. Build the Docker image:
   ```
   docker build -t kafka-core-java .
   ```

2. Run the Docker container:
   ```
   docker run kafka-core-java
   ```

Note: The Dockerfile uses OpenJDK 17, which is compatible with the Java 21 bytecode produced by the project.