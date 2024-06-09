package org.semicorp.mscitemapi.domain.question;

import lombok.extern.slf4j.Slf4j;
import org.semicorp.mscitemapi.domain.question.dto.QuestionFullDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/question")
@Slf4j
public class QuestionController {

    private  final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }


    @GetMapping
    public ResponseEntity<List<QuestionFullDTO>> getAllQuestions(
                            @RequestHeader(HttpHeaders.AUTHORIZATION) String token)  {
        log.info("Get all questions");
        List<QuestionFullDTO> userQuestions = questionService.findAll();
        return new ResponseEntity<>(userQuestions, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<QuestionFullDTO>> getUserQuestions(
                            @PathVariable(value="userId") String userId)  {
        //log.info("Get questions for user  id: {}", userId);
        List<QuestionFullDTO> userQuestions = questionService.getUserQuestions(userId);
        return new ResponseEntity<>(userQuestions, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Question> addQuestion(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                      @RequestBody Question question) {
        Question result = questionService.insert(question);
        if (result == null) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

}
