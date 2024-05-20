package org.semicorp.mscitemapi.question;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class QuestionService {

    public List<Question> getUserQuestions(String userId) {
        log.info("Get questions for user id: {}",  userId);
        ArrayList<Question> questions = new ArrayList<>();
        questions.add(new Question("id1", "How to train LTSM", "Need help with training LSTM ..."));
        questions.add(new Question("id2", "How to build B+Tree index for a database?", "My task is to build B+Tree index ..."));
        return questions;
    }
}
