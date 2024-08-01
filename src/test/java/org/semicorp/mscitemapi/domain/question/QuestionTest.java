package org.semicorp.mscitemapi.domain.question;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuestionTest {

    private Question question;

    @BeforeEach
    void setUp() {
        question = QuestionTestUtils.createBasicQuestion();
    }

    @Test
    void testGetters() {
        assertEquals("id1", question.getId());
        assertEquals("Question 1", question.getTitle());
        assertEquals("Lorem ipsum", question.getContent());
        assertEquals("userId1", question.getUserId());
        assertEquals("UserName", question.getUserName());
        assertEquals("collegeId1", question.getCollegeId());
        assertEquals("moduleId1", question.getModuleId());
        assertEquals(ItemStatus.ACTIVE, question.getStatus());
        assertEquals(LocalDateTime.of(2024, 8, 1, 10,0, 0), question.getDateCreated());
        assertEquals(LocalDateTime.of(2024, 8, 1, 10,0, 0), question.getDateModified());
        assertEquals("hash123", question.getHash());
    }

}