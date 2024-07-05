package org.semicorp.mscitemapi.domain.answer;

import lombok.extern.slf4j.Slf4j;
import org.semicorp.mscitemapi.domain.question.dto.QuestionFullAnswersCountDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/answer")
@Slf4j
public class AnswerController {

    private  final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }


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

    @PostMapping ResponseEntity<Answer> addAnswer(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                  @RequestBody Answer answer) {
        System.out.println(answer);
        Answer result = answerService.insert(answer);
        if (result == null) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    ResponseEntity<Answer> updateAnswer(@PathVariable(value = "id") String answerId,
                                        @RequestBody Answer answer) {
        log.info("Answer update request. ID: {},  {}", answerId, answer);
        Answer findAnswer = answerService.getById(answerId);
        answer.setId(Optional.ofNullable(answer.getId()).orElse(findAnswer.getId()));
        answer.setContent(Optional.ofNullable(answer.getContent()).orElse(findAnswer.getContent()));
        answer.setUserId(Optional.ofNullable(answer.getUserId()).orElse(findAnswer.getUserId()));
        answer.setQuestionId(Optional.ofNullable(answer.getQuestionId()).orElse(findAnswer.getQuestionId()));
        answer.setUserName(Optional.ofNullable(answer.getUserName()).orElse(findAnswer.getUserName()));
        answer.setDateCreated(Optional.ofNullable(answer.getDateCreated()).orElse(findAnswer.getDateCreated()));
        answer.setStatus(Optional.ofNullable(answer.getStatus()).orElse(findAnswer.getStatus()));
        answer.setBest(Optional.ofNullable(answer.isBest()).orElse(findAnswer.isBest()));
        answer.setDateModified(LocalDateTime.now());
        Answer reponse = answerService.updateAnswer(answerId, answer);
        if(reponse != null) {
            log.info("Answer updated");
            return new ResponseEntity<>(reponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/best/{id}")
    ResponseEntity<Answer> updateAnswerBestValue(@PathVariable(value = "id") String answerId,
                                        @RequestParam("best") boolean best) {
        log.info("Answer id: {} set `best` value to: {}", answerId, best);

        boolean response = answerService.setBestValue(answerId, best);
        Answer findAnswer = answerService.getById(answerId);
        if(response) {
            log.info("Answer `best` set to: {}", best);
            return new ResponseEntity<>(findAnswer,  HttpStatus.BAD_REQUEST);
        }
        log.info("Error while setting Answer `best` value to: {}", best);
        return new ResponseEntity<>(null,  HttpStatus.BAD_REQUEST);
    }

}
