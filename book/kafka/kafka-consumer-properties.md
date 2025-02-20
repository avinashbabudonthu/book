# Kafka consumer properties
* Write below properties to connect Producer to kafka
```
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;

private Properties kafkaProducerProperties() {
	Properties properties = new Properties();
	// bootstrap. servers
	properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
	// key.deserializer
	properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
	// value.deserializer
	properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
	// group.id
	properties.put(ConsumerConfig.GROUP_ID_CONFIG, "group-1");

	// auto.offset.reset
	// earliest: automatically reset the offset to the earliest offset
	// latest: automatically reset the offset to the latest offset
	// none: throw exception to the consumer if no previous offset is found for the consumer's group</li>
	// anything else: throw exception to the consumer
	properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

	// enable.auto.commit
	properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");

	// auto.commit.interval.ms
	// with auto commit set to true then offset auto commit will happen every 5 sec based on below property
	properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 5000);

	// partition.assignment.strategy
	properties.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, CooperativeStickyAssignor.class.getName());

	// group.instance.id
	properties.put(ConsumerConfig.GROUP_INSTANCE_ID_CONFIG, "consumerGracefulShutdown-" + UUID.randomUUID());

	// session.timeout.ms
	properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 30000);

	return properties;
}
```