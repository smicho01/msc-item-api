package org.semicorp.mscitemapi.domain.tag;

import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Jdbi;
import org.semicorp.mscitemapi.domain.tag.dao.tagquestion.TagQuestionDAO;
import org.semicorp.mscitemapi.domain.tag.dao.tagquestion.TagQuestionRow;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TagQuestionService {

    private final Jdbi jdbi;

    public TagQuestionService(Jdbi jdbi) {
        this.jdbi = jdbi;
    }


    public TagQuestion insert(TagQuestion tagQuestion) {
        try {
            boolean insert = jdbi.onDemand(TagQuestionDAO.class).insert(new TagQuestionRow(tagQuestion));
        } catch(Exception e) {
            log.error("Can't insert TagQuestion: {}", tagQuestion);
            return null;
        }
        log.info("Assigned tag id {} to post id {}", tagQuestion.getTagId(), tagQuestion.getQuestionId() );
        return tagQuestion;
    }
}
