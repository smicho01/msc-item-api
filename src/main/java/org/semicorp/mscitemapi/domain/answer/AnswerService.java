package org.semicorp.mscitemapi.domain.answer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.Update;
import org.semicorp.mscitemapi.domain.answer.dao.AnswerDAO;
import org.semicorp.mscitemapi.domain.answer.dao.AnswerRow;
import org.semicorp.mscitemapi.domain.question.ItemStatus;
import org.semicorp.mscitemapi.domain.question.dto.QuestionFullAnswersCountDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AnswerService {

    private final Jdbi jdbi;

    public List<Answer> getAllAnswersWithStatus(String status) {
        return jdbi.onDemand(AnswerDAO.class).findAllAnswersWithStatus(status);
    }

    public List<Answer> getAllAnswersByQuestionId(String questionId) {
        return jdbi.onDemand(AnswerDAO.class).findAllByQuestionId(questionId);
    }

    public List<Answer> getAllAnswersByQuestionIdAndStatus(String questionId, String status) {
        return jdbi.onDemand(AnswerDAO.class).findAllByQuestionIdAndStatus(questionId, status);
    }

    /**
     * Finds all active answers and all answers with all statuses for a given user ID and question ID.
     *
     * @param questionId The ID of the question to search for.
     * @param userId     The ID of the user to search for.
     * @return A list of Answer objects containing all the active answers and all the answers with all statuses for the given user ID and question ID.
     */
    public List<Answer> getAllActiveAndAllStatusesForUserId(String questionId, String userId) {
        return jdbi.onDemand(AnswerDAO.class).findAllActiveAndAllStatusesForUserId(questionId, userId);
    }

    public Answer insert(Answer answer) {
        answer.setId(UUID.randomUUID().toString());
        answer.setStatus(ItemStatus.PENDING);
        try {
            jdbi.onDemand(AnswerDAO.class).insert(new AnswerRow(answer));
        } catch (Exception e) {
            log.error("Can't insert answer for question id: {}", answer.getQuestionId());
            return null;
        }
        log.info("Added answer with id: {} for question id: {}", answer.getId(), answer.getQuestionId());
        return answer;
    }

    public List<Answer> findByUserIdShort(String userId, String status, Integer limit) {
        //return jdbi.onDemand(QuestionDAO.class).findAllByUserIdShort(userId);
        log.info("Called 'answer.findByUserIdShort' for userId: {}, status: {}, limit: {}", userId, status, limit);
        String statusSqlPart = "";
        String limitSqlPart = " LIMIT 100 ";
        if (status != null) {
            statusSqlPart = String.format(" AND LOWER(a.status) = LOWER(%s)\n", status);
        }

        if (limit != null) {
            limitSqlPart = String.format(" LIMIT %d", limit);
        }

        log.info("findAllShortStatus with status: {} and limit: {}", status, limit);
        String sql = "SELECT a.* FROM items.answer  as a " +
                "WHERE userid = '" + userId + "'" +
                statusSqlPart +
                "ORDER BY a.datecreated DESC\n" +
                limitSqlPart;

        List<Answer> response = jdbi.withHandle(handle -> handle.createQuery(sql)
                .mapToBean(Answer.class)
                .list());
        log.info("Response results: {}", response.size());
        return response;
    }

    public Answer getById(String answerId) {
        log.info("Get answer by id: {}", answerId);
        String sql = "SELECT * FROM items.answer WHERE id = :answerId;";
        Optional<Answer> response = jdbi.withHandle(handle -> handle.createQuery(sql)
                .bind("answerId", answerId)
                .mapToBean(Answer.class)
                .findFirst());
        if (response.isEmpty()) {
            log.info("Not found. Answer id: {}", answerId);
            return null;
        }
        log.info("Answer found");
        return response.get();
    }

    public Answer updateAnswer(String id, Answer answerDetails) {
        return jdbi.withExtension(AnswerDAO.class, dao -> {
            Answer existingAnswer = dao.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Answer not found with id " + id));
            existingAnswer.setContent(answerDetails.getContent());
            existingAnswer.setUserId(answerDetails.getUserId());
            existingAnswer.setQuestionId(answerDetails.getQuestionId());
            existingAnswer.setUserName(answerDetails.getUserName());
            existingAnswer.setDateModified(LocalDateTime.now());
            existingAnswer.setStatus(answerDetails.getStatus());
            existingAnswer.setBest(answerDetails.isBest());
            dao.updateAnswer(existingAnswer);
            return existingAnswer;
        });
    }

    public boolean setBestValue(String answerId, boolean best) {
        try (Handle handle = jdbi.open()) {
            try (Update update = handle.createUpdate("UPDATE items.answer SET best = :best WHERE id = :answerId")) {
                update.bind("best", best)
                        .bind("answerId", answerId)
                        .execute();
            } catch (Exception e) {
                log.error("Error updating best value for answer id: {}, Error: {}", answerId, e.getMessage());
                return false;
            }

        }
        return true;
    }
}
