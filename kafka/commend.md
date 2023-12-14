### 도커 카프카 컨테이너 접속
docker exec -it kafka /bin/bash

## 토픽 명령어

### 토픽 생성
kafka-topics.sh --create --topic topic1 --bootstrap-server localhost:9092 --replication-factor 3 --partitions 3

### 토픽 리스트 확인
kafka-topics.sh --list --bootstrap-server localhost:9092

### 토픽 상세 조회
kafka-topics.sh --describe --topic topic1 --bootstrap-server kafka:9092

### 토픽 삭제
kafka-topics.sh --delete --bootstrap-server kafka:9092 --topic topic1

### 토픽 파티션 개수 변경 (4개로 변경)
kafka-topics.sh --bootstrap-server kfserver:9092 --topic hello.kafka --alter --partitions 4

### 토픽 리텐션 기간 변경 (1일로 변경)
kafka-configs.sh --bootstrap-server kfserver:9092 --entity-type topics --entity-name hello.kafka --alter --add-config retention.ms=86400000

### 토픽 리텐션 확인
kafka-configs.sh --bootstrap-server kfserver:9092 --entity-type topics --entity-name hello.kafka --describe

### 토픽에 record 삽입 (프로듀서, record - key, value 쌍으로 이루어져 있음)
kafka-console-producer.sh --bootstrap-server kfserver:9092 --topic hello.kafka
kafka-console-producer.sh --bootstrap-server kfserver:9092 --topic hello.kafka --property "parse.key=true" --property "key.separator=:"   // key를 사용하며, key, value 구분자는 클론(:)으로 설정

### 토픽 record 조회 (컨슈머)
kafka-console-consumer.sh --bootstrap-server kfserver:9092 --topic hello.kafka --from-beginning --property print.key=true --property key.separator="-" --group hello-group
> group을 통해 신규 컨슈머 그룹 생성. 컨슈머 그룹을 통해 읽은 메시지에 대해 커밋한다.
> 
> 커밋은 컨슈머가 특정 레코드까지 처리를 완료했다고 레코드의 오프셋 번호를 카프카 브로커에 저장하는 행위. 커밋 정보는 __consumer_offsets 이름의 내부 토픽에 저장됨.