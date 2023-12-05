package com.example.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscribeMessage {

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "topic1", groupId = "my-consumer")
    public void listenGroup1(String messageText,
                             @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) throws JsonProcessingException {
        var message = objectMapper.readValue(messageText, Message.class);

        log.info("Received Message in group my-consumer:: [{}]-{}", partition, message);
    }

    @Setter
    @Getter
    @ToString
    private static class Message {
        private String id;
        private String message;
        private LocalDateTime sendDateTime;
    }
}
