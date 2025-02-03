# Kafka setup with Docker
* Docker compose files
  * [Start Zookeeper and 1 Kafka broker and Kafka UI](kafka-1-broker-with-ui.yml)
  * [Start Zookeeper and 2 Kafka brokers and Kafka UI](kafka-2-brokers-ui.yml)
  * [Start Zookeeper and 1 Kafka broker and Kafka UI with our custom network](kafka-1-broker-ui-network.yml)
* In this setup, our Zookeeper server is listening on port=2181 for the kafka service, which is defined within the same container setup. However, for any client running on the host, it'll be exposed on port 22181.
* Similarly, the kafka service is exposed to the host applications through port 29092, but it is actually advertised on port 9092 within the container environment configured by the KAFKA_ADVERTISED_LISTENERS property.
* Start Kafka Server. Open `cmd`. Run below command
```
docker compose -f C:\path-to-docker-compose-yaml-file\file-name.yaml up -d
or
docker-compose -f C:\path-to-docker-compose-yaml-file\file-name.yaml up -d
```
* Wait for 2 minutes for Kakfka UI to initiate
* Zookeeper url - `http://localhost:22181`
* Kafka broker url - `http://localhost:29092`
* Connect to kafka UI using tool like `Offset Explorer`
* Open Kafka UI - http://localhost:8080
* Stop containers
```
docker compose -f C:\path-to-docker-compose-yaml-file\file-name.yaml down
or
docker-compose -f C:\path-to-docker-compose-yaml-file\file-name.yaml down
```
------
# Reference
* https://www.baeldung.com/ops/kafka-docker-setup