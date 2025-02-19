# Kafka producer properties
* Write below properties to connect Producer to kafka
```
import java.util.Properties;
import org.apache.kafka.clients.producer.ProducerConfig;

private Properties kafkaProducerProperties() {
	Properties properties = new Properties();
	// bootstrap.servers
	properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
	// key.serializer
	properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
	// value.serializer
	properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
	properties.put(ProducerConfig.ACKS_CONFIG, "all");
	properties.put(ProducerConfig.RETRIES_CONFIG, "3");
	// The producer groups together any records that arrive in between request transmissions into a single batched request.
	// Normally this occurs only under load when records arrive faster than they can be sent out.
	// However in some circumstances the client may want to reduce the number of requests even under moderate load
	properties.put(ProducerConfig.LINGER_MS_CONFIG, "1");
	// ensure we don't push duplicates
	properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
	// batch.size
	// not recommended for production. Default size is 16 KB
	properties.put(ProducerConfig.BATCH_SIZE_CONFIG, "400");
	// partitioner.class
	properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, RoundRobinPartitioner.class.getName());

	return properties;
}
```