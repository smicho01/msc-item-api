package org.semicorp.mscitemapi.domain.question.mappers;

import org.semicorp.mscitemapi.domain.question.Question;
import org.semicorp.mscitemapi.domain.question.ItemStatus;
import org.semicorp.mscitemapi.domain.question.dto.AddQuestionDTO;
import org.semicorp.mscitemapi.domain.question.dto.QuestionFullDTO;
import org.semicorp.mscitemapi.domain.question.dto.QuestionFullWithTagsDTO;
import org.semicorp.mscitemapi.domain.tag.Tag;

import java.util.List;

public class QuestionMapper {

    public static Question addQuestionDtoToQuestion(AddQuestionDTO addQuestionDTO) {
        return Question.builder()
                .title(addQuestionDTO.getTitle())
                .content(addQuestionDTO.getContent())
                .userId(addQuestionDTO.getUserId())
                .userName(addQuestionDTO.getUserName())
                .collegeId(addQuestionDTO.getCollegeId())
                .moduleId(addQuestionDTO.getModuleId())
                .status(ItemStatus.PENDING)
                .hash(addQuestionDTO.getHash())
                .build();
    }

    public static QuestionFullWithTagsDTO toQuestionWithTags(QuestionFullDTO questionFullDTO, List<Tag> tagsList) {
        return QuestionFullWithTagsDTO.builder()
                .id(questionFullDTO.getId())
                .title(questionFullDTO.getTitle())
                .content(questionFullDTO.getContent())
                .userId(questionFullDTO.getUserId())
                .userName(questionFullDTO.getUserName())
                .collegeId(questionFullDTO.getCollegeId())
                .collegeName(questionFullDTO.getCollegeName())
                .moduleId(questionFullDTO.getModuleId())
                .moduleName(questionFullDTO.getModuleName())
                .status(questionFullDTO.getStatus())
                .dateCreated(questionFullDTO.getDateCreated())
                .dateModified(questionFullDTO.getDateModified())
                .tags(tagsList)
                .hash(questionFullDTO.getHash())
                .build();
    }
}