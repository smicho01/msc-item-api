package org.semicorp.mscitemapi.domain.question;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Jdbi;
import org.semicorp.mscitemapi.domain.question.dao.QuestionDAO;
import org.semicorp.mscitemapi.domain.question.dao.QuestionRow;
import org.semicorp.mscitemapi.domain.question.dto.QuestionFullDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class QuestionService {

    private final Jdbi jdbi;


    public List<QuestionFullDTO> findAll() {
        return jdbi.onDemand(QuestionDAO.class).findAll();
    }

    /* SQL query truncate field `content` to specified length [100] characters */
    public List<QuestionFullDTO> findAllShort() {
        return jdbi.onDemand(QuestionDAO.class).findAllShort();
    }

    public List<QuestionFullDTO> findByUserId(String userId) {
        return jdbi.onDemand(QuestionDAO.class).findAllByUserId(userId);
    }

    public List<QuestionFullDTO> findByUserIdShort(String userId) {
        return jdbi.onDemand(QuestionDAO.class).findAllByUserIdShort(userId);
    }

    public List<QuestionFullDTO> findQuestionsByTagId(String tagId) {
        return jdbi.onDemand(QuestionDAO.class).findQuestionsByTagId(tagId);
    }

    public Question insert(Question question) {
        question.setId(UUID.randomUUID().toString());
        question.setStatus(QuestionStatus.PENDING);
        // TODO: [improvement] check if question has been asked (calculate hash and compare?)
        try {
            log.info("Attempt to add question. Id: {} for user id: {}", question.getId(), question.getUserId());
            boolean insert = jdbi.onDemand(QuestionDAO.class).insert(new QuestionRow(question));
            if (!insert) {
                log.info("Question not added. Id: {}", question.getId());
                return null;
            }
        } catch (Exception e) {
            log.error("Error while inserting new question with id: {}", question.getId());
            return null;
        }
        log.info("Question added. Id: {}", question.getId());
        return question;
    }

    public List<QuestionFullDTO> getUserQuestionsShort(String userId) {
        return jdbi.onDemand(QuestionDAO.class).findAllShort();
    }
}
