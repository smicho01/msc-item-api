package org.semicorp.mscitemapi.domain.question;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/question")
@Slf4j
public class QuestionController {

    private  final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Question>> getUserQuestions(@PathVariable(value="userId") String userId)  {
        //log.info("Get questions for user  id: {}", userId);
        List<Question> userQuestions = questionService.getUserQuestions(userId);
        return new ResponseEntity<>(userQuestions, HttpStatus.OK);
    }

}
