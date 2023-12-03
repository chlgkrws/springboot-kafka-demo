package com.example.producer.message;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageSender {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "topic1";

    @Scheduled(fixedRate = 5000)
    public void send() {
        var message = Message.builder()
                .id(UUID.randomUUID().toString())
                .message("test")
                .sendDateTime(LocalDateTime.now())
                .build();

        log.info("Send!");

        kafkaTemplate.send(TOPIC, message);
    }

    @Builder
    private static class Message {
        private String id;
        private String message;
        private LocalDateTime sendDateTime;
    }
}
