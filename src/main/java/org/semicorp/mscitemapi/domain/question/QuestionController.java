package org.semicorp.mscitemapi.domain.question;

import lombok.extern.slf4j.Slf4j;
import org.semicorp.mscitemapi.domain.question.dto.AddQuestionDTO;
import org.semicorp.mscitemapi.domain.question.dto.QuestionFullDTO;
import org.semicorp.mscitemapi.domain.question.mappers.QuestionMapper;
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

    @GetMapping("/short")
    public ResponseEntity<List<QuestionFullDTO>> getAllQuestionsShort(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token)  {
        log.info("Get all questions");
        List<QuestionFullDTO> userQuestions = questionService.findAllShort();
        return new ResponseEntity<>(userQuestions, HttpStatus.OK);
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<QuestionFullDTO>> getUserQuestions(
                            @PathVariable(value="userId") String userId)  {
        //log.info("Get questions for user  id: {}", userId);
        List<QuestionFullDTO> userQuestions = questionService.findByUserId(userId);
        return new ResponseEntity<>(userQuestions, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/short")
    public ResponseEntity<List<QuestionFullDTO>> getUserQuestionsShort(
            @PathVariable(value="userId") String userId)  {
        //log.info("Get questions for user  id: {}", userId);
        List<QuestionFullDTO> userQuestions = questionService.findByUserIdShort(userId);
        return new ResponseEntity<>(userQuestions, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Question> addQuestion(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                      @RequestBody AddQuestionDTO addQuestionDTO) {

        Question result = questionService.insert(QuestionMapper.addQuestionDtoToQuestion(addQuestionDTO));

        // TODO: insert tags

        if (result == null) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

}
