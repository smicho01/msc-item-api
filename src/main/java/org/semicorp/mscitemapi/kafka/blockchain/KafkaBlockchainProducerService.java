package org.semicorp.mscitemapi.kafka.blockchain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaBlockchainProducerService {

    private final KafkaTemplate<String, Object> kafkaTagsListTemplate;

    private String TOPIC = "blockchain-best-answer";

    public KafkaBlockchainProducerService(KafkaTemplate<String, Object> kafkaTagsListTemplate) {
        this.kafkaTagsListTemplate = kafkaTagsListTemplate;
    }

    public void sendMessage(Object message) {
        log.info("Sending message {} to Kafka topic {}", message.toString(), TOPIC);
        kafkaTagsListTemplate.send(TOPIC, message);
    }
}
