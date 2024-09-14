package org.semicorp.mscitemapi.domain.answer;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.Update;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.semicorp.mscitemapi.domain.answer.dao.AnswerDAO;
import org.semicorp.mscitemapi.domain.answer.dao.AnswerRow;
import org.semicorp.mscitemapi.domain.question.ItemStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AnswerServiceTest {

    @Mock
    private Jdbi jdbi;

    @Mock
    private Handle handle;

    @Mock
    private Update update;

    @Mock
    private AnswerDAO answerDAO;

    @InjectMocks
    private AnswerService answerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAnswersWithStatus() {
        Answer answer = new Answer();
        answer.setId(UUID.randomUUID().toString());
        answer.setStatus(ItemStatus.ACTIVE);

        when(jdbi.onDemand(AnswerDAO.class)).thenReturn(answerDAO);
        when(answerDAO.findAllAnswersWithStatus(anyString())).thenReturn(List.of(answer));

        List<Answer> answers = answerService.getAllAnswersWithStatus("ACTIVE");

        assertNotNull(answers);
        assertEquals(1, answers.size());
        assertEquals(ItemStatus.ACTIVE, answers.get(0).getStatus());
    }

    @Test
    void testGetAllAnswersByQuestionId() {
        Answer answer = new Answer();
        answer.setId(UUID.randomUUID().toString());
        answer.setQuestionId("q1");

        when(jdbi.onDemand(AnswerDAO.class)).thenReturn(answerDAO);
        when(answerDAO.findAllByQuestionId(anyString())).thenReturn(List.of(answer));

        List<Answer> answers = answerService.getAllAnswersByQuestionId("q1");

        assertNotNull(answers);
        assertEquals(1, answers.size());
        assertEquals("q1", answers.get(0).getQuestionId());
    }

    @Test
    void testInsertAnswer() {
        Answer answer = new Answer();
        answer.setQuestionId("q1");
        answer.setContent("Sample Answer");

        when(jdbi.onDemand(AnswerDAO.class)).thenReturn(answerDAO);
        when(answerDAO.insert(any(AnswerRow.class))).thenReturn(true);

        Answer insertedAnswer = answerService.insert(answer);

        assertNotNull(insertedAnswer);
        assertEquals("Sample Answer", insertedAnswer.getContent());
        verify(answerDAO, times(1)).insert(any(AnswerRow.class));
    }

    @Test
    void testInsertAnswer_Fail() {
        Answer answer = new Answer();
        answer.setQuestionId("q1");

        when(jdbi.onDemand(AnswerDAO.class)).thenReturn(answerDAO);
        doThrow(new RuntimeException("Insert failed")).when(answerDAO).insert(any(AnswerRow.class));

        Answer result = answerService.insert(answer);

        assertNull(result);
        verify(answerDAO, times(1)).insert(any(AnswerRow.class));
    }

    @Test
    void testGetById_Success() {
        Answer answer = new Answer();
        answer.setId("a1");

        when(jdbi.withHandle(any())).thenReturn(Optional.of(answer));

        Answer result = answerService.getById("a1");

        assertNotNull(result);
        assertEquals("a1", result.getId());
    }

    @Test
    void testGetById_NotFound() {
        when(jdbi.withHandle(any())).thenReturn(Optional.empty());

        Answer result = answerService.getById("a1");

        assertNull(result);
    }
}
