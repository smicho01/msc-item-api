package org.semicorp.mscitemapi.service.malicious;

import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Jdbi;
import org.semicorp.mscitemapi.domain.answer.Answer;
import org.semicorp.mscitemapi.domain.question.dto.QuestionFullDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MaliciousBehaviorTrackerService {

    private final Jdbi jdbi;
    private final int thresholdOfNonSuspiciousBestAnswers = 4;
    private int numberOfBestAnswersSelected;
    private final String interval = "1 DAY";
    private final int thresholdOfNonSuspiciousBestAnswersInInterval = 2;

    public MaliciousBehaviorTrackerService(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    /**
     * Check if there is any malicious behaviour e.g. the same users select their answers as the best to get tokens
     * If flags severity score (cumulated) is above 1000, then return as malicious behaviour
     * @param answer Answer To get answer author id
     * @param questionDto Question To get question author id
     * @return integer Cumulated flags severity score
     */
    public Boolean spotMaliciousBehaviour(Answer answer, QuestionFullDTO questionDto) {
        numberOfBestAnswersSelected = 0;

        Boolean hasRepetitiveMaliciousBehaviour = checkForRepetitiveBehaviour(questionDto.getUserId(), answer.getUserId());
        Boolean hasRepetitiveMaliciousBehaviourInInterval = isActivityInShortPeriod(questionDto.getUserId(), answer.getUserId(), interval);

        int flagsSeverityScore = analyseFlags(questionDto.getUserId(), answer.getUserId());

        return flagsSeverityScore > 1000;
    }

    /* This method will check if user repetitively check the other user answers as the best with threshold of: X */
    public Boolean checkForRepetitiveBehaviour(String questionAuthorId, String answerAuthorId) {
        log.info("Check for repetitive behaviour for question author id: {} and answer author id: {}", questionAuthorId, answerAuthorId);

        String sql = "SELECT questionAuthorId, answerAuthorId, COUNT(*) " +
                " FROM items.bestanswer " +
                " WHERE questionAuthorId = :questionAuthorId  " +
                " AND answerAuthorId = :answerAuthorId " +
                " GROUP BY questionAuthorId, answerAuthorId " +
                " HAVING COUNT(*) > :thresholdOfNonSuspiciousBestAnswers; ";

        Optional<BestAnswerCountResponse> response = jdbi.withHandle(handle -> handle.createQuery(sql)
                .bind("questionAuthorId", questionAuthorId)
                .bind("answerAuthorId", answerAuthorId)
                .bind("thresholdOfNonSuspiciousBestAnswers", thresholdOfNonSuspiciousBestAnswers)
                .mapToBean(BestAnswerCountResponse.class).findFirst());

        if(response.isPresent()) {
            BestAnswerCountResponse bestAnswerCountResponse = response.get();
            numberOfBestAnswersSelected = bestAnswerCountResponse.getCount();
            log.warn("Check for repetitive behaviour gives result: {} for threshold {}",
                        numberOfBestAnswersSelected, thresholdOfNonSuspiciousBestAnswers);
            if(numberOfBestAnswersSelected > thresholdOfNonSuspiciousBestAnswers) {
                log.warn("Flagged as potential malicious behaviour.");
                insertMaliciousBehaviour(questionAuthorId, answerAuthorId, Levels.LOW, Reasons.REPETITIVE_BEHAVIOUR);
                return false;
            }
        }
        log.info("Not flagged as malicious behaviour");
        return true;
    }

    public Boolean areUsersFriends(String questionAuthorId, String answerAuthorId) {
        // check with User Service if both are friends.
        return false;
    }

    public Boolean isActivityInShortPeriod(String questionAuthorId, String answerAuthorId, String interval)  {
        log.info("Check for repetitive behaviour in interval: {} for question author id: {} and answer author id: {}",
                                interval, questionAuthorId, answerAuthorId);

        String sql = "SELECT questionAuthorId, answerAuthorId, COUNT(*) " +
            " FROM items.bestanswer " +
            " WHERE timestamp > (NOW() - INTERVAL '"+interval+"') " +
            " AND questionAuthorId = :questionAuthorId  " +
            " AND answerAuthorId = :answerAuthorId " +
            " GROUP BY questionAuthorId, answerAuthorId " +
            " HAVING COUNT(*) > :thresholdOfNonSuspiciousBestAnswersInInterval;";

        Optional<BestAnswerCountResponse> response = jdbi.withHandle(handle -> handle.createQuery(sql)
                .bind("questionAuthorId", questionAuthorId)
                .bind("answerAuthorId", answerAuthorId)
                .bind("thresholdOfNonSuspiciousBestAnswersInInterval", thresholdOfNonSuspiciousBestAnswersInInterval)
                .mapToBean(BestAnswerCountResponse.class).findFirst());

        if(response.isPresent()) {
            BestAnswerCountResponse bestAnswerCountResponse = response.get();
            numberOfBestAnswersSelected = bestAnswerCountResponse.getCount();
            log.warn("Check for repetitive behaviour in timestamp {} gives result: {} for threshold: {} ",
                        interval, numberOfBestAnswersSelected, thresholdOfNonSuspiciousBestAnswersInInterval );
            if(numberOfBestAnswersSelected > thresholdOfNonSuspiciousBestAnswersInInterval) {
                log.warn("Flagged as potential malicious behaviour within interval.");
                insertMaliciousBehaviour(questionAuthorId, answerAuthorId, Levels.MEDIUM, Reasons.REPETITIVE_BEHAVIOUR_INTERVAL);
                return false;
            }
        }
        log.info("Not flagged as malicious behaviour for a given time interval: {}", interval);
        return true;
    }

    /**
     * This one will calculate all existing flags and assign points according to the flag severity.
     * Low *1 ; Medium * 100, High * 1000
     * @param questionAuthorId
     * @param answerAuthorId
     * @return integer total severity points
     */
    private int analyseFlags(String questionAuthorId, String answerAuthorId) {
        log.info("Analyse flags for question owner: {} and answer owner: {}", questionAuthorId, answerAuthorId);
        int totalScore = 0;
        List<FlaggedUserLevelResponse> usersFlagsCount = getUsersFlagsCount(questionAuthorId, answerAuthorId);
        log.info("Found levels: {}", usersFlagsCount.size());
        for (FlaggedUserLevelResponse response: usersFlagsCount) {
            if (response.getLevel().equals(Levels.LOW.toString())) {
                totalScore += response.getCount();
            } else if(response.getLevel().equals(Levels.MEDIUM.toString())) {
                totalScore += response.getCount() * 100;
            } else if (response.getLevel().equals(Levels.HIGH.toString())) {
                totalScore += response.getCount() * 1000;
            }
        }
        log.info("Total score: {}", totalScore);

        return totalScore;
    }

    public List<FlaggedUserLevelResponse> getUsersFlagsCount(String questionAuthorId, String answerAuthorId) {
        String sql = "SELECT level, COUNT(*) AS count " +
                " FROM items.flaggedusers " +
                " WHERE (questionowner = :questionowner " +
                " AND answerowner = :answerowner ) " +
                " OR (questionowner = :questionowner " +
                " AND answerowner = :answerowner ) " +
                " GROUP BY level;";

        List<FlaggedUserLevelResponse> flaggedUserLevelResponses = jdbi.withHandle(handle -> handle.createQuery(sql)
                .bind("questionowner", questionAuthorId)
                .bind("answerowner", answerAuthorId)
                .mapToBean(FlaggedUserLevelResponse.class).stream().toList());
        return flaggedUserLevelResponses;
    }


    public void insertMaliciousBehaviour(String questionAuthorId, String answerAuthorId, Levels level,  String reason) {
        log.info("Saving malicious behaviour in flaggedusers table for user id: {}, and: {} with reason: {}",
                        questionAuthorId, answerAuthorId, reason);
        try {
            String sql = "INSERT INTO items.flaggedusers (questionOwner, answerOwner, level, reason) VALUES (?, ?, ?, ?);";
            Integer i = jdbi.withHandle(handle -> handle.execute(sql, questionAuthorId, answerAuthorId, level, reason));
        } catch (Exception e) {
            log.error("Error while saving malicious behaviour in flaggedusers. ERROR: {}", e.getMessage());
        }
    }
}
