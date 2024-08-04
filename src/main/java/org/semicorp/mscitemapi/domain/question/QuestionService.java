package org.semicorp.mscitemapi.domain.question;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Jdbi;
import org.semicorp.mscitemapi.domain.nlp.NlpService;
import org.semicorp.mscitemapi.domain.question.dao.QuestionDAO;
import org.semicorp.mscitemapi.domain.question.dao.QuestionRow;
import org.semicorp.mscitemapi.domain.question.dto.QuestionFullAnswersCountDTO;
import org.semicorp.mscitemapi.domain.question.dto.QuestionFullDTO;
import org.semicorp.mscitemapi.domain.question.dto.QuestionFullWithTagsDTO;
import org.semicorp.mscitemapi.domain.question.mappers.QuestionMapper;
import org.semicorp.mscitemapi.domain.tag.Tag;
import org.semicorp.mscitemapi.domain.tag.TagService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class QuestionService {

    private final Jdbi jdbi;
    private final TagService tagService;
    private final NlpService nlpService;

    public QuestionService(Jdbi jdbi, TagService tagService, NlpService nlpService) {
        this.jdbi = jdbi;
        this.tagService = tagService;
        this.nlpService = nlpService;
    }


    public List<QuestionFullDTO> findAll(Integer limit) {
        try {
            log.info("Executing jdbi findAll() with limit {}", limit);
            return jdbi.onDemand(QuestionDAO.class).findAll(limit);
        } catch (Exception e){
            log.error("Error while executing jdbi findAll(). ERROR: {}", e.getMessage());
            return null;
        }
    }

    /* SQL query truncate field `content` to specified length [100] characters */
    public List<QuestionFullDTO> findAllShort() {
        return jdbi.onDemand(QuestionDAO.class).findAllShort();
    }

    public List<QuestionFullAnswersCountDTO> findAllShortStatus(String status, int limit) {
        log.info("findAllShortStatus with status: {} and limit: {}", status, limit);
        String sql = "SELECT q.*, m.name as moduleName, c.name as collegeName, COUNT(a.id) AS answersCount\n" +
                "FROM  items.question q\n" +
                "JOIN  items.module m ON q.moduleid = m.id\n" +
                "JOIN  items.college c ON q.collegeid = c.id\n" +
                "LEFT JOIN  items.answer a ON q.id = a.questionId\n" +
                "WHERE  LOWER(q.status) = LOWER(:status)\n" +
                "GROUP BY q.id, m.name, c.name\n" +
                "ORDER BY q.dateCreated DESC\n" +
                "LIMIT :limit;";

        List<QuestionFullAnswersCountDTO> questions = jdbi.withHandle(handle -> handle.createQuery(sql)
                    .bind("status", status)
                    .bind("limit", limit)
                    .mapToBean(QuestionFullAnswersCountDTO.class)
                    .list());
        return questions;
    }

    public QuestionFullDTO findById(String questionId) {
        return jdbi.onDemand(QuestionDAO.class).findById(questionId);
    }

    public List<QuestionFullDTO> findByUserId(String userId) {
        return jdbi.onDemand(QuestionDAO.class).findAllByUserId(userId);
    }

    public List<QuestionFullAnswersCountDTO> findByUserIdShort(String userId, String status, Integer limit) {
        //return jdbi.onDemand(QuestionDAO.class).findAllByUserIdShort(userId);
        log.info("Called 'question.findByUserIdShort' for userId: {}, status: {}, limit: {}", userId, status, limit);
        String statusSqlPart = "";
        String limitSqlPart = " LIMIT 100 ";
        if(status !=null) {
            statusSqlPart = String.format(" AND LOWER(q.status) = LOWER(%s)\n", status);
        }

        if(limit != null) {
            limitSqlPart = String.format(" LIMIT %d", limit);
        }

        log.info("findAllShortStatus with status: {} and limit: {}", status, limit);
        String sql = "SELECT q.*, m.name as moduleName, c.name as collegeName, COUNT(a.id) AS answersCount\n" +
                "FROM  items.question q\n" +
                "JOIN  items.module m ON q.moduleid = m.id\n" +
                "JOIN  items.college c ON q.collegeid = c.id\n" +
                "LEFT JOIN  items.answer a ON q.id = a.questionId\n" +
                "WHERE q.userid = '" + userId + "'" +
                statusSqlPart +
                "GROUP BY q.id, m.name, c.name\n" +
                "ORDER BY q.dateCreated DESC\n" +
                limitSqlPart;

        List<QuestionFullAnswersCountDTO> questions = jdbi.withHandle(handle -> handle.createQuery(sql)
                    .mapToBean(QuestionFullAnswersCountDTO.class)
                    .list());
        log.info("Response results: {}", questions.size());
        return questions;
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
        question.setStatus(ItemStatus.ACTIVE);
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

        // Create embedding
        try {
            nlpService.createEmbedding(question);
        } catch (Exception e) {
            log.error("Error while creating embedding. Error: {}", e.getMessage());
        }

        return question;
    }

    public List<QuestionFullDTO> getUserQuestionsShort(String userId) {
        return jdbi.onDemand(QuestionDAO.class).findAllShort();
    }

    public List<QuestionFullDTO> findByTitleLIKE(String phrase, String collegeId) {
        log.info("Get question by title like: {}", phrase);
        try {
            String collegeWhereClause = "";
            if (collegeId != null) {
                log.info("Search only for collegeId: {}", collegeId);
                collegeWhereClause = String.format(" AND q.collegeid = '%s'  ", collegeId);
            }

            String sql = "SELECT q.* , m.name as moduleName, c.name as collegeName " +
                    " FROM items.question as q , items.college as c, items.module as m " +
                    " WHERE q.collegeId = c.id AND q.moduleId = m.id " +
                    " AND q.status = 'ACTIVE' " +
                    " AND LOWER(q.title) LIKE LOWER(:phrase) " +
                    collegeWhereClause +
                    " ORDER BY q.datecreated DESC;";

            List<QuestionFullDTO> results = jdbi.withHandle(handle -> handle.createQuery(sql)
                    .bind("phrase", "%" + phrase + "%")
                    .mapToBean(QuestionFullDTO.class)
                    .list());

            log.info("Found results: {}", results.size());
            return results;
        } catch (Exception e) {
            log.error("Error while executing findByTitleLIKE. ERROR: {}", e.getMessage());
            return null;
        }
    }
}
