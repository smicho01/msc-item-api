package org.semicorp.mscitemapi.domain.answer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.semicorp.mscitemapi.domain.bestanswer.BestAnswer;
import org.semicorp.mscitemapi.domain.bestanswer.BestAnswerService;
import org.semicorp.mscitemapi.domain.question.ItemStatus;
import org.semicorp.mscitemapi.domain.question.QuestionService;
import org.semicorp.mscitemapi.domain.question.dto.QuestionFullDTO;
import org.semicorp.mscitemapi.kafka.blockchain.KafkaBlockchainProducerService;
import org.semicorp.mscitemapi.service.malicious.MaliciousBehaviorTrackerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AnswerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AnswerService answerService;

    @Mock
    private BestAnswerService bestAnswerService;

    @Mock
    private QuestionService questionService;

    @Mock
    private KafkaBlockchainProducerService kafkaBlockchainProducerService;

    @Mock
    private MaliciousBehaviorTrackerService maliciousBehaviorTrackerService;

    @InjectMocks
    private AnswerController answerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(answerController).build();
    }

    @Test
    void testGetActiveAnswers() throws Exception {
        Answer answer1 = new Answer("a1", "content1", "q1", "u1", "user1",
                                ItemStatus.ACTIVE,  false, LocalDateTime.now(), LocalDateTime.now(), null);
        Answer answer2 = new Answer("a2", "content2", "q2", "u2", "user2",
                                ItemStatus.ACTIVE,  false, LocalDateTime.now(), LocalDateTime.now(), null);
        List<Answer> answers = Arrays.asList(answer1, answer2);

        when(answerService.getAllAnswersWithStatus(anyString())).thenReturn(answers);

        mockMvc.perform(get("/api/v1/answer?status=ACTIVE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("a1"))
                .andExpect(jsonPath("$[1].id").value("a2"));
    }

    @Test
    void testGetAnswerById() throws Exception {
        Answer answer = new Answer("a1", "content1", "q1", "u1", "user1",
                                ItemStatus.ACTIVE,  false, LocalDateTime.now(), LocalDateTime.now(), null);

        when(answerService.getById(anyString())).thenReturn(answer);

        mockMvc.perform(get("/api/v1/answer/a1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("a1"))
                .andExpect(jsonPath("$.content").value("content1"));
    }

    @Test
    void testGetAnswersForQuestionId() throws Exception {
        Answer answer1 = new Answer("a1", "content1", "q1", "u1", "user1",
                                ItemStatus.ACTIVE,  false, LocalDateTime.now(), LocalDateTime.now(), null);
        List<Answer> answers = Arrays.asList(answer1);

        when(answerService.getAllAnswersByQuestionId(anyString())).thenReturn(answers);

        mockMvc.perform(get("/api/v1/answer/question/q1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("a1"));
    }

    @Test
    void testAddAnswer() throws Exception {
        Answer answer = new Answer("a1", "content1", "q1", "u1", "user1",
                                ItemStatus.ACTIVE,  false, LocalDateTime.now(), LocalDateTime.now(), null);

        when(answerService.insert(any(Answer.class))).thenReturn(answer);

        mockMvc.perform(post("/api/v1/answer")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\":\"content1\", \"questionId\":\"q1\", \"userId\":\"u1\", \"userName\":\"user1\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("a1"))
                .andExpect(jsonPath("$.content").value("content1"));
    }

    @Test
    void testUpdateAnswerBestValue() throws Exception {
        Answer answer = new Answer("a1", "content1", "q1", "u1", "user1",
                                ItemStatus.ACTIVE,  true, LocalDateTime.now(), LocalDateTime.now(), null);
        QuestionFullDTO questionDto = new QuestionFullDTO();

        when(answerService.getById(anyString())).thenReturn(answer);
        when(questionService.findById(anyString())).thenReturn(questionDto);
        when(maliciousBehaviorTrackerService.spotMaliciousBehaviour(any(Answer.class), any(QuestionFullDTO.class))).thenReturn(false);
        when(answerService.setBestValue(anyString(), anyBoolean())).thenReturn(true);
        when(bestAnswerService.save(any(BestAnswer.class))).thenReturn(true);

        mockMvc.perform(put("/api/v1/answer/best/a1?best=true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("a1"))
                .andExpect(jsonPath("$.best").value(true));
    }

    @Test
    void testUpdateAnswer() throws Exception {
        Answer answer = new Answer("a1", "newContent", "q1", "u1", "user1",
                ItemStatus.PENDING,  true, LocalDateTime.now(), LocalDateTime.now(), null);

        when(answerService.getById(anyString())).thenReturn(answer);
        when(answerService.updateAnswer(anyString(), any(Answer.class))).thenReturn(answer);

        mockMvc.perform(put("/api/v1/answer/a1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\":\"newContent\", \"status\":\"ACTIVE\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("a1"))
                .andExpect(jsonPath("$.content").value("newContent"));
    }
}
