package org.semicorp.mscitemapi.kafka.tag;

import lombok.extern.slf4j.Slf4j;
import org.semicorp.mscitemapi.domain.tag.TagService;
import org.semicorp.mscitemapi.kafka.tag.entity.QuestionTagsList;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaTagConsumerService {

    private final TagService tagService;

    public KafkaTagConsumerService(TagService tagService) {
        this.tagService = tagService;
    }

    @KafkaListener(topics = "tags-topic", groupId = "tags-group-id",
                    containerFactory = "kafkaTagsListenerContainerFactory")
    public void consumeTagsList(QuestionTagsList questionTagsList) {
        log.info("Consumed tags message: {}", questionTagsList);
        tagService.assignTagsToQuestion(questionTagsList);
    }
}
