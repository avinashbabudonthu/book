# Send Avro messages
------
* Add below dependencies. Refer maven central repository for latest versions
```
<dependency>
    <groupId>io.confluent</groupId>
    <artifactId>kafka-schema-registry-client</artifactId>
    <version>5.3.0</version>
</dependency>
<dependency>
    <groupId>org.apache.avro</groupId>
    <artifactId>avro</artifactId>
    <version>1.11.4</version>
</dependency>
<dependency>
    <groupId>io.confluent</groupId>
    <artifactId>kafka-avro-serializer</artifactId>
    <version>5.2.1</version>
</dependency>
<dependency>
    <groupId>io.confluent</groupId>
    <artifactId>kafka-streams-avro-serde</artifactId>
    <version>5.3.0</version>
    <exclusions>
        <exclusion>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-log4j12</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```
* Add plugin to maven pom.xml
```
<plugin>
    <groupId>org.apache.avro</groupId>
    <artifactId>avro-maven-plugin</artifactId>
    <version>1.8.2</version>
    <executions>
        <execution>
            <phase>generate-sources</phase>
            <goals>
                <goal>schema</goal>
            </goals>
            <configuration>
                <sourceDirectory>${project.basedir}/src/main/resources/avro</sourceDirectory>
                <outputDirectory>${project.basedir}/src/main/java/</outputDirectory>
                <stringType>String</stringType>
            </configuration>
        </execution>
    </executions>
</plugin>
```
* create `avro` directory under `src\main\resources`
* create file `user.avsc`. Copy below content
```
{
  "namespace": "com.app.avro.model",
  "type": "record",
  "name": "User",
  "fields": [
    {
      "name": "name",
      "type": "string",
      "avro.java.string": "String"
    },
    {
      "name": "age",
      "type": "int"
    }
  ]
}
```
* Run maven command. This generate `User.java` file in package `com.app.avro.model` in `src\main\java`
```
mvn clean compile package
```
* Send message
```
@Test
void sendAvroMessage() throws InterruptedException {
    Properties properties = getProperties();
    properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
    properties.setProperty("schema.registry.url", "http://127.0.0.1:8081");
//        properties.setProperty("schema.registry.url", "http://localhost:9091,http://localhost:9092,http://localhost:9093");
//        properties.setProperty("schema.registry.url", "http://localhost:9091");

    Callback callback = (RecordMetadata recordMetadata, Exception e) ->
            log.info("message sent, topic={}, partition={}, offset={}",
                    recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset());

    Producer<String, User> producer = new KafkaProducer<>(properties);
    String topic = "test.user.avro";
    for (int i = 0; i < 100; i++) {
        String key = new SimpleDateFormat("SSS").format(new Date());
        User value = User.newBuilder().setName(FAKER.name().fullName()).setAge(FAKER.number().randomDigit()).build();
        log.info("message sending, i={}, key={}, value={}", i, key, value);
        ProducerRecord<String, User> producerRecord = new ProducerRecord<>(topic, key, value);
        producer.send(producerRecord, callback);
        Thread.sleep(1000);
    }

    producer.close();

}
```
* Refer [ProducerTest.sendAvroMessage method](kafka-core-java/src/main/java/com/kafka/ProducerTest.java)