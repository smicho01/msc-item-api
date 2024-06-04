package org.semicorp.mscitemapi.domain.answer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/answer")
@Slf4j
public class AnswerController {

    private  final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Answer>> getUserAnswers(@PathVariable(value="userId") String userId)  {
        //log.info("Get answers for user id: {}", userId);
        List<Answer> userAnswers = answerService.getUserAnswers(userId);
        return new ResponseEntity<>(userAnswers, HttpStatus.OK);
    }

}
