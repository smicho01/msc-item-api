package org.semicorp.mscitemapi.domain.answer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AnswerService {

    public List<Answer> getUserAnswers(String userId) {
        ArrayList<Answer> answers = new ArrayList<>();
        answers.add(new Answer("id1", "idQ1", userId, "Some answer #1"));
        answers.add(new Answer("id2", "idQ2", userId, "Some answer #2"));
        answers.add(new Answer("id3", "idQ2", userId, "Some answer #3"));
        return answers;
    }
}
