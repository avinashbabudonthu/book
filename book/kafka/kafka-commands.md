# Kafka commands
------
* Pre requisite - [setup Kafka using docker](setup-with-docker.md)
* Create topic
```
kafka-topics.bat --zookeeper localhost:2181 --create --topic [topic-name] --partitions 1 --replication-factor 1
```
* Create topic with log compact
```
kafka-topics.bat --zookeeper localhost:2181 --create --topic [topic-name] --partitions 1 --replication-factor 1 --config cleanup.policy=compact --config min.cleanable.dirty.ratio=0.005 --config segment.ms=10000
```
* Describe Topic
```
kafka-topics.bat --zookeeper localhost:2181 --describe --topic [Topic-Name]
```
* List topics
```
kafka-topics.bat --zookeeper localhost:2181 --list
```
* Delete Topic
```
kafka-run-class.bat --zookeeper localhost:2181 --delete --topic [topic-name] kafka.admin.TopicCommand
```
* Kafka producer to send message
```
kafka-console-producer.bat --broker-list localhost:9092 --topic [topic-name]
```
* Kafka consumer to receive message
```
kafka-console-consumer.bat --zookeeper localhost:2181 --topic [topic-name]

kafka-console-consumer.bat --zookeeper localhost:2181 --topic [topic-name] --from-beginning --property print.key=true --property key.separator=,

kafka-console-consumer.bat --zookeeper localhost:2181 --topic [topic-name] --from-beginning --formatter kafka.tools.DefaultMessageFormatter --property print.key=true --property print.value=true --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer --property value.deserializer=org.apache.kafka.common.serialization.StringDeserializer
```
* Read messages from beginning
```
kafka-console-consumer.bat --zookeeper localhost:2181 --topic [Topic-Name] --from-beginning
```
* Dry run - Reset the offsets to the beginning of each partition
```
kafka-consumer-groups.bat --bootstrap-server localhost:29092 --group my-group-1 --reset-offsets --to-earliest --topic topic-1 --dry-run
```
* `execute` flag is needed to reset offsets
```
kafka-consumer-groups.bat --bootstrap-server localhost:29092 --group my-group-1 --reset-offsets --to-earliest --topic topic-1 --execute
```
* Describe consumer groups
```
kafka-consumer-groups.bat --bootstrap-server localhost:29092 --describe --group my-group-1
```