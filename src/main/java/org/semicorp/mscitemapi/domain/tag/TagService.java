package org.semicorp.mscitemapi.domain.tag;

import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Jdbi;
import org.semicorp.mscitemapi.kafka.tag.entity.QuestionTagsList;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TagService {


    private final Jdbi jdbi;

    public TagService(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public void assignTagsToQuestion(QuestionTagsList questionTagsList) {
        log.info("Assigning tags list to question id: {}", questionTagsList.getQuestionId());
    }
}
