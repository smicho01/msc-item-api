package org.semicorp.mscitemapi.domain.question;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Jdbi;
import org.semicorp.mscitemapi.domain.question.dao.QuestionDAO;
import org.semicorp.mscitemapi.domain.question.dto.QuestionFullDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class QuestionService {

    private final Jdbi jdbi;

    public List<Question> getUserQuestions(String userId) {
        ArrayList<Question> questions = new ArrayList<>();
        return questions;
    }

    public List<QuestionFullDTO> findAll() {
        return jdbi.onDemand(QuestionDAO.class).findAll();
    }
}
