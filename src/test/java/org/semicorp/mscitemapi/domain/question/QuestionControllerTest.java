package org.semicorp.mscitemapi.domain.question;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.semicorp.mscitemapi.domain.question.dto.AddQuestionDTO;
import org.semicorp.mscitemapi.domain.question.dto.QuestionFullDTO;
import org.semicorp.mscitemapi.domain.question.dto.QuestionFullWithTagsDTO;
import org.semicorp.mscitemapi.kafka.tag.KafkaTagProducerService;
import org.semicorp.mscitemapi.utils.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class QuestionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private QuestionService questionService;

    @Mock
    private KafkaTagProducerService kafkaTagProducerService;

    @InjectMocks
    private QuestionController questionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(questionController).build();
    }

    @Test
    void testGetAllQuestions() throws Exception {
        QuestionFullDTO question1 = new QuestionFullDTO();
        question1.setTitle("Test question 1");
        QuestionFullDTO question2 = new QuestionFullDTO();
        question2.setTitle("Test question 2");
        List<QuestionFullDTO> questions = Arrays.asList(question1, question2);

        when(questionService.findAll(any(Integer.class))).thenReturn(questions);

        mockMvc.perform(get("/api/v1/question?limit=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test question 1"))
                .andExpect(jsonPath("$[1].title").value("Test question 2"));
    }

    @Test
    void testGetQuestionById() throws Exception {
        QuestionFullWithTagsDTO question = new QuestionFullWithTagsDTO();
        question.setId("q1");
        question.setTitle("Test question");

        when(questionService.findQuestionWithTags(anyString())).thenReturn(question);

        mockMvc.perform(get("/api/v1/question/q1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test question"));
    }

    @Test
    void testAddQuestion() throws Exception {
        AddQuestionDTO addQuestionDTO = new AddQuestionDTO();
        String questionHash = StringUtils.generateHash("user1", "Test question", "content");

        Question question = new Question();
        question.setId("q1");
        question.setHash(questionHash);

        when(questionService.insert(any(Question.class))).thenReturn(question);

        mockMvc.perform(post("/api/v1/question")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Test question\",\"content\":\"content\",\"userName\":\"user1\",\"tags\":[\"tag1\",\"tag2\"]}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("q1"));
    }

    @Test
    void testGetUserQuestions() throws Exception {
        QuestionFullDTO question1 = new QuestionFullDTO();
        question1.setTitle("Test question 1");
        question1.setUserId("user1");
        List<QuestionFullDTO> questions = Arrays.asList(question1);

        when(questionService.findByUserId(anyString())).thenReturn(questions);

        mockMvc.perform(get("/api/v1/question/user/user1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test question 1"));
    }
}
