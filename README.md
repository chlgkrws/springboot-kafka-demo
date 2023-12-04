# springboot-kafka-demo

이 프로젝트는 Spring Boot, Java 및 Gradle을 활용하여 구축되었으며, Docker Compose를 통해 Apache Kafka와 Zookeeper의 설정을 간편하게 진행했습니다. 

또한, Kafdrop을 사용하여 Kafka 클러스터의 상태를 쉽고 명확하게 시각적으로 파악할 수 있습니다.


## Run

### 1. kafka / zookeeper / kafdrop 실행
```shell
# 백그라운드 실행 (카프카/주키퍼/카프드롭)
docker-compose up -d

# 백그라운드 실행 종료
docker-compose down
```
### 2. 애플리케이션 실행
```shell
./gradlew build

# producer 실행
java -jar build/libs/producer.jar

# consumer 실행
java -jar build/libs/consumer.jar
```

### 3. 실행 확인
- producer: localhost:8090

- consumer: localhost:8091

- Kafdrop: localhost:9100


## Reference
https://kafka.apache.org/quickstart \
https://www.baeldung.com/spring-kafka \
https://6mini.github.io/data%20engineering/2022/04/17/docker/
