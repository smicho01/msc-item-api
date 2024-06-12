package org.semicorp.mscitemapi.domain.question.mappers;

import org.semicorp.mscitemapi.domain.question.Question;
import org.semicorp.mscitemapi.domain.question.QuestionStatus;
import org.semicorp.mscitemapi.domain.question.dto.AddQuestionDTO;

public class QuestionMapper {

    public static Question addQuestionDtoToQuestion(AddQuestionDTO addQuestionDTO) {
        return Question.builder()
                .title(addQuestionDTO.getTitle())
                .content(addQuestionDTO.getContent())
                .userId(addQuestionDTO.getUserId())
                .userName(addQuestionDTO.getUserName())
                .collegeId(addQuestionDTO.getCollegeId())
                .moduleId(addQuestionDTO.getModuleId())
                .status(QuestionStatus.PENDING)
                .build();
    }
}