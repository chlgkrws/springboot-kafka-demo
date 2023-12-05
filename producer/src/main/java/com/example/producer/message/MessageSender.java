package com.example.producer.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageSender {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "topic1";

    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 5000)
    public void send() throws JsonProcessingException {
        var message = Message.builder()
                .id(UUID.randomUUID().toString())
                .message("test")
                .sendDateTime(LocalDateTime.now())
                .build();

        String messageText = objectMapper.writeValueAsString(message);

        CompletableFuture<SendResult<String, String>> sendResultFuture = kafkaTemplate.send(TOPIC, messageText);
        sendResultFuture.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Sent message=[{}] with offset=[{}]", message, result.getRecordMetadata().offset());
            } else {
                log.warn("Unable to send message=[{}] due to : {}", message, ex.getMessage());
            }
        });

    }

    @Getter
    @Builder
    private static class Message {
        private String id;
        private String message;
        private LocalDateTime sendDateTime;
    }
}
