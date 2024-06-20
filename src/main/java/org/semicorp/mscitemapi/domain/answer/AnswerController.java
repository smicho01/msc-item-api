package org.semicorp.mscitemapi.domain.answer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("{questionId}")
    public ResponseEntity<List<Answer>> getAnswersForQuestionId(@PathVariable(value="questionId") String questionId)  {
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

    @PostMapping ResponseEntity<Answer> addAnswer(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                  @RequestBody Answer answer) {
        System.out.println(answer);
        Answer result = answerService.insert(answer);
        if (result == null) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

}
