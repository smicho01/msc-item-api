package org.semicorp.mscitemapi.domain.question.dto;

import lombok.*;
import org.semicorp.mscitemapi.domain.question.QuestionStatus;
import org.semicorp.mscitemapi.domain.tag.Tag;

import java.util.List;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QuestionFullWithTagsDTO {

    private String id;
    private String title;
    private String content;
    private String userId;
    private String userName;
    private String collegeId;
    private String collegeName;
    private String moduleId;
    private String moduleName;
    private QuestionStatus status;
    private List<Tag> tags;
}