package org.semicorp.mscitemapi.domain.bestanswer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BestAnswerService {

    private final Jdbi jdbi;

    public Boolean save(BestAnswer bestAnswer) {
        try {
            String sql = "INSERT INTO items.bestanswer " +
                    "(questionAuthorId,answerAuthorId, questionId,answerId, timestamp ) " +
                    "VALUES (?, ?, ?, ?, ?);";
            jdbi.withHandle(handle -> handle.execute(sql,
                        bestAnswer.getQuestionAuthorId(), bestAnswer.getAnswerAuthorId(),
                        bestAnswer.getQuestionId(), bestAnswer.getAnswerId(), bestAnswer.getTimestamp()));
            log.info("Best answer saved: {}", bestAnswer);
            return true;
        } catch (Exception e) {
            log.error("Unable to save best answer: {} , Errror: {}", bestAnswer, e.getMessage());
            return false;
        }
    }
}
