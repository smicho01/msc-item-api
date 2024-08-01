package org.semicorp.mscitemapi.domain.question;

import java.time.LocalDateTime;

public class QuestionTestUtils {

    public static Question createBasicQuestion() {
        return  new Question("id1","Question 1", "Lorem ipsum", "userId1",
                "UserName", "collegeId1", "moduleId1", ItemStatus.ACTIVE,
                LocalDateTime.of(2024, 8, 1, 10,0, 0),
                LocalDateTime.of(2024, 8, 1, 10,0, 0),
                "hash123");
    }
}
