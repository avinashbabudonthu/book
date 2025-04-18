# Kafka Confluent Platform Setup in windows machine Using Docker
* Clone repo - https://github.com/confluentinc/cp-all-in-one.git
```
git clone https://github.com/confluentinc/cp-all-in-one.git
```
* Clones the repo `cp-all-in-one`
* Navigate to `cp-all-in-one/cp-all-in-one` in `cmd` or `powershell`
* Execute command 
```
docker-compose up --build
```
* To run in detach mode
```
docker-compose -d up --build
```
![picture](img/000001-kafka-confluent-platform-docker-compose-command-execution.jpg)
* After starting check the status
```
docker-compose ps
or
docker ps -a
or
docker ps
```
* Open url - [http://localhost:9021](http://localhost:9021)

* Check the `control-center` port using `docker-compose ps` command result
![picture](img/000002-kafka-confluent-control-center.jpg)
