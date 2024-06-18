package org.semicorp.mscitemapi.kafka.tag;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaTagProducerService {

    private final KafkaTemplate<String, Object> kafkaTagsListTemplate;

    @Value("${app.kafka.topic.tags}")
    private String TOPIC;

    public KafkaTagProducerService(KafkaTemplate<String, Object> kafkaTagsListTemplate) {
        this.kafkaTagsListTemplate = kafkaTagsListTemplate;
    }

    public void sendMessage(Object message) {
        log.info("Sending message {} to Kafka topic {}", message.toString(), TOPIC);
        kafkaTagsListTemplate.send(TOPIC, message);
    }
}
