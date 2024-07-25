package org.semicorp.mscitemapi.domain.question;

import lombok.extern.slf4j.Slf4j;
import org.semicorp.mscitemapi.domain.question.dto.AddQuestionDTO;
import org.semicorp.mscitemapi.domain.question.dto.QuestionFullAnswersCountDTO;
import org.semicorp.mscitemapi.domain.question.dto.QuestionFullDTO;
import org.semicorp.mscitemapi.domain.question.dto.QuestionFullWithTagsDTO;
import org.semicorp.mscitemapi.domain.question.mappers.QuestionMapper;
import org.semicorp.mscitemapi.kafka.tag.KafkaTagProducerService;
import org.semicorp.mscitemapi.kafka.tag.entity.QuestionTagsList;
import org.semicorp.mscitemapi.utils.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/question")
@Slf4j
public class QuestionController {

    private final QuestionService questionService;
    private final KafkaTagProducerService kafkaTagProducerService;

    public QuestionController(QuestionService questionService, KafkaTagProducerService kafkaTagProducerService) {
        this.questionService = questionService;
        this.kafkaTagProducerService = kafkaTagProducerService;
    }


    @GetMapping
    public ResponseEntity<List<QuestionFullDTO>> getAllQuestions(
                        @RequestHeader(HttpHeaders.AUTHORIZATION) String token)  {
        log.info("Get all questions");
        List<QuestionFullDTO> userQuestions = questionService.findAll();
        return new ResponseEntity<>(userQuestions, HttpStatus.OK);
    }

    @GetMapping("{questionId}")
    public ResponseEntity<QuestionFullWithTagsDTO> getQuestionById(
            @PathVariable(value="questionId") String questionId)  {
        QuestionFullWithTagsDTO userQuestions = questionService.findQuestionWithTags(questionId);
        return new ResponseEntity<>(userQuestions, HttpStatus.OK);
    }

    @GetMapping("/short")
    public ResponseEntity<List<QuestionFullAnswersCountDTO>> getAllQuestionsShort(
                        @RequestParam(value = "status", required = false) String status,
                        @RequestParam(value = "limit", required = false) Integer limit )  {
        log.info("Calling ge all questions short");
        if(limit == null) {
            limit = 50;
        }
        if(status != null) {
            List<QuestionFullAnswersCountDTO> response = questionService.findAllShortStatus(status, limit);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        //List<QuestionFullDTO> response = questionService.findAllShort();
        List<QuestionFullAnswersCountDTO> response = questionService.findAllShortStatus("ACTIVE", 50);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<QuestionFullDTO>> getUserQuestions(
                        @PathVariable(value="userId") String userId)  {
        //log.info("Get questions for user  id: {}", userId);
        List<QuestionFullDTO> userQuestions = questionService.findByUserId(userId);
        return new ResponseEntity<>(userQuestions, HttpStatus.OK);
    }

    /* User SQL search with LIKE clause to get titles containing given phrase */
    @GetMapping("/like/{phrase}")
    public ResponseEntity<List<QuestionFullDTO>> getUserTitleLIKE(
            @PathVariable(value="phrase") String phrase)  {
        log.info("Get question by title LIKE : {}", phrase);
        List<QuestionFullDTO> questions = questionService.findByTitleLIKE(phrase);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/short")
    public ResponseEntity<List<QuestionFullAnswersCountDTO>> getUserQuestionsShort(
                        @PathVariable(value="userId") String userId)  {
        log.info("Get questions 'short' for user  id: {}", userId);
        List<QuestionFullAnswersCountDTO> userQuestions = questionService.findByUserIdShort(userId,null, null);
        return new ResponseEntity<>(userQuestions, HttpStatus.OK);
    }

    @GetMapping("/tagid/{tagId}")
    public ResponseEntity<List<QuestionFullDTO>> getQuestionsByTagId(
            @PathVariable(value="tagId") String tagId)  {

        List<QuestionFullDTO> userQuestions = questionService.findQuestionsByTagId(tagId);
        return new ResponseEntity<>(userQuestions, HttpStatus.OK);
    }

    @GetMapping("/tagname/{tagName}")
    public ResponseEntity<List<QuestionFullDTO>> getQuestionsByTagName(
            @PathVariable(value="tagName") String tagName)  {

        List<QuestionFullDTO> userQuestions = questionService.findQuestionsByTagName(tagName);
        return new ResponseEntity<>(userQuestions, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Question> addQuestion(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                        @RequestBody AddQuestionDTO addQuestionDTO) {
        String questionHash = StringUtils.generateHash(addQuestionDTO.getUserName(),
                        addQuestionDTO.getTitle(), addQuestionDTO.getContent());
        addQuestionDTO.setHash(questionHash);
        Question result = questionService.insert(QuestionMapper.addQuestionDtoToQuestion(addQuestionDTO));

        if (result == null) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        // Send tags list to Kafka to insert/assign them async.
        // Check KafkaTagConsumerService.consumeTagsList() method for insert logic
        if(!addQuestionDTO.getTags().isEmpty()) {
            QuestionTagsList questionTagsList = new QuestionTagsList(result.getId(), addQuestionDTO.getTags());
            kafkaTagProducerService.sendMessage(questionTagsList);
        }

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

}
