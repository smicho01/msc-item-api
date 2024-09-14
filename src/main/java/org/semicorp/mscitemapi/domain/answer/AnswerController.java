package org.semicorp.mscitemapi.domain.answer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.semicorp.mscitemapi.domain.bestanswer.BestAnswer;
import org.semicorp.mscitemapi.domain.bestanswer.BestAnswerService;
import org.semicorp.mscitemapi.domain.blockchain.BlockchainMsgBestAnswer;
import org.semicorp.mscitemapi.domain.question.QuestionService;
import org.semicorp.mscitemapi.domain.question.dto.QuestionFullDTO;
import org.semicorp.mscitemapi.kafka.blockchain.KafkaBlockchainProducerService;
import org.semicorp.mscitemapi.service.malicious.MaliciousBehaviorTrackerService;
import org.semicorp.mscitemapi.utils.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/answer")
@Slf4j
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;
    private final BestAnswerService bestAnswerService;
    private final QuestionService questionService;
    private final KafkaBlockchainProducerService kafkaBlockchainProducerService;
    private final MaliciousBehaviorTrackerService maliciousBehaviorTrackerService;


    @GetMapping
    public ResponseEntity<List<Answer>> getActiveAnswers(@RequestParam(value = "status", required = false) String status)  {
        List<Answer> response = answerService.getAllAnswersWithStatus(status);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("{answerId}")
    public ResponseEntity<Answer> getAnswerById(@PathVariable(value="answerId") String answerId)  {
        log.info("Get answers by id: {}", answerId);
        Answer response = answerService.getById(answerId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity<List<Answer>> getAnswersForQuestionId(@PathVariable(value="questionId") String questionId)  {
        log.info("Get answers for question id: {}", questionId);
        List<Answer> response = answerService.getAllAnswersByQuestionId(questionId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("{questionId}/status/{status}")
    public ResponseEntity<List<Answer>> getAnswersForQuestionWithStatus(
                            @PathVariable(value="questionId") String questionId,
                            @PathVariable(value="status") String status)  {
        List<Answer> response = answerService.getAllAnswersByQuestionIdAndStatus(questionId, status);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /* Get all 'Active' answers or all status type for a given user */
    @GetMapping("{questionId}/user/{userId}")
    public ResponseEntity<List<Answer>> getAllActiveOrAnyStatusForUserId(
            @PathVariable(value="questionId") String questionId,
            @PathVariable(value="userId") String userId)  {
        List<Answer> response = answerService.getAllActiveAndAllStatusesForUserId(questionId, userId);
        log.info("Get all active answers for question id {} or all statuses for user id {} answers. Size: {} ", questionId, userId, response.size());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/short")
    public ResponseEntity<List<Answer>> getUserQuestionsShort(
            @PathVariable(value="userId") String userId)  {
        log.info("Get questions 'short' for user  id: {}", userId);
        List<Answer> userQuestions = answerService.findByUserIdShort(userId,null, null);
        return new ResponseEntity<>(userQuestions, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Answer> addAnswer(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                  @RequestBody Answer answer) {
        log.info("Add answer endpoint called");
        try {
            // TODO: answers hash may be used to avoid adding the same question over and over.
            String answerHash = StringUtils.generateHash(answer.getUserName(), answer.getContent(), answer.getQuestionId());
            answer.setHash(answerHash);
            Answer result = answerService.insert(answer);
            if (result == null) {
                log.warn("Result of adding answer is null");
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
            log.info("Answer added. Id: {}", result.getId());
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error while adding answer:  ERROR: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<Answer> updateAnswer(@PathVariable(value = "id") String answerId,
                                        @RequestBody Answer answer) {
        log.info("Answer update request. ID: {},  {}", answerId, answer);
        Answer findAnswer = answerService.getById(answerId);
        extracted(answer, findAnswer);
        Answer reponse = answerService.updateAnswer(answerId, answer);
        if(reponse != null) {
            log.info("Answer updated");
            return new ResponseEntity<>(reponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Selects best answer and ignite process of assigning tokens to user
     * @param answerId String
     * @param best Boolean
     * @return Response entity for class Answer
     */
    @PutMapping("/best/{id}")
    ResponseEntity<Answer> updateAnswerBestValue(@PathVariable(value = "id") String answerId,
                                        @RequestParam("best") boolean best) {
        log.info("Answer id: {} set `best` value to: {}", answerId, best);
        try {

            // Find answer data
            Answer answer = answerService.getById(answerId);
            // Find question data for that answer
            QuestionFullDTO questionDto = questionService.findById(answer.getQuestionId());

            // TODO: add check for malicious actions here
            Boolean isMaliciousAction = maliciousBehaviorTrackerService.spotMaliciousBehaviour(answer, questionDto);
            if(isMaliciousAction) {
                log.error("Malicious behaviour spotted. Will not proceed with selecting best answer");
                return new ResponseEntity<>(null, HttpStatus.CONFLICT);
            }

            boolean response = answerService.setBestValue(answerId, best);
            Answer updatedAnswer = answerService.getById(answerId);
            if (response) {
                log.info("Answer `best` set to: {}", best);
                // Start token assignment process
                UUID messageUUID = UUID.randomUUID(); //  To be used later in response message identification
                BlockchainMsgBestAnswer bm = new BlockchainMsgBestAnswer(messageUUID, updatedAnswer.getId(),
                                updatedAnswer.getQuestionId(), updatedAnswer.getUserId());
                kafkaBlockchainProducerService.sendMessage(bm); // Send message to Kafka topic

                Date date = new java.util.Date();
                Timestamp timestamp = new java.sql.Timestamp(date.getTime());
                BestAnswer bestAnswer = new BestAnswer(questionDto.getUserId(), updatedAnswer.getUserId(),
                        questionDto.getId(), updatedAnswer.getId(),timestamp );
                Boolean bestAnswerSaved = bestAnswerService.save(bestAnswer);
                return new ResponseEntity<>(updatedAnswer, HttpStatus.OK);
            }
            log.warn("Answer `best` could not be set to: {}", best);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.info("Error while setting Answer `best` value to: {}. ERROR: {}", best, e.getMessage());
            return new ResponseEntity<>(null,  HttpStatus.BAD_REQUEST);
        }
    }

    private static void extracted(Answer answer, Answer findAnswer) {
        answer.setId(Optional.ofNullable(answer.getId()).orElse(findAnswer.getId()));
        answer.setContent(Optional.ofNullable(answer.getContent()).orElse(findAnswer.getContent()));
        answer.setUserId(Optional.ofNullable(answer.getUserId()).orElse(findAnswer.getUserId()));
        answer.setQuestionId(Optional.ofNullable(answer.getQuestionId()).orElse(findAnswer.getQuestionId()));
        answer.setUserName(Optional.ofNullable(answer.getUserName()).orElse(findAnswer.getUserName()));
        answer.setDateCreated(Optional.ofNullable(answer.getDateCreated()).orElse(findAnswer.getDateCreated()));
        answer.setStatus(Optional.ofNullable(answer.getStatus()).orElse(findAnswer.getStatus()));
        answer.setBest(Optional.ofNullable(answer.isBest()).orElse(findAnswer.isBest()));
        answer.setDateModified(LocalDateTime.now());
    }

}
