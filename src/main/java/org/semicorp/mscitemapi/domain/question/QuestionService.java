package org.semicorp.mscitemapi.domain.question;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Jdbi;
import org.semicorp.mscitemapi.domain.question.dao.QuestionDAO;
import org.semicorp.mscitemapi.domain.question.dao.QuestionRow;
import org.semicorp.mscitemapi.domain.question.dto.QuestionFullDTO;
import org.semicorp.mscitemapi.domain.question.dto.QuestionFullWithTagsDTO;
import org.semicorp.mscitemapi.domain.question.mappers.QuestionMapper;
import org.semicorp.mscitemapi.domain.tag.Tag;
import org.semicorp.mscitemapi.domain.tag.TagService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class QuestionService {

    private final Jdbi jdbi;
    private final TagService tagService;


    public List<QuestionFullDTO> findAll() {
        return jdbi.onDemand(QuestionDAO.class).findAll();
    }

    /* SQL query truncate field `content` to specified length [100] characters */
    public List<QuestionFullDTO> findAllShort() {
        return jdbi.onDemand(QuestionDAO.class).findAllShort();
    }

    public List<QuestionFullDTO> findAllShortStatus(String status, int limit) {
        log.info("findAllShortStatus with status: {} and limit: {}", status, limit);
        String sql = "SELECT q.*, m.name as moduleName, c.name as collegeName " +
                " FROM items.question as q, items.module as m, items.college as c " +
                " WHERE q.moduleid = m.id AND q.collegeid = c.id " +
                " AND LOWER(status) = LOWER(:status) ORDER BY q.datecreated DESC LIMIT :limit;";

        List<QuestionFullDTO> questions = jdbi.withHandle(handle -> {
            return handle.createQuery(sql)
                    .bind("status", status)
                    .bind("limit", limit)
                    .mapToBean(QuestionFullDTO.class)
                    .list();

        });
        return questions;
    }

    public QuestionFullDTO findById(String questionId) {
        return jdbi.onDemand(QuestionDAO.class).findById(questionId);
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

    public List<QuestionFullDTO> findQuestionsByTagName(String tagName) {
        return jdbi.onDemand(QuestionDAO.class).findQuestionsByTagName(tagName);
    }

    public QuestionFullWithTagsDTO findQuestionWithTags(String questionId) {
        QuestionFullDTO foundQuestion = findById(questionId);
        List<Tag> tagsForQuestionId = tagService.findTagsForQuestionId(questionId);
        QuestionFullWithTagsDTO questionWithTags = QuestionMapper.toQuestionWithTags(
                    foundQuestion, tagsForQuestionId);
        return questionWithTags;
    }

    public Question insert(Question question) {
        question.setId(UUID.randomUUID().toString());
        question.setStatus(ItemStatus.PENDING);
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
