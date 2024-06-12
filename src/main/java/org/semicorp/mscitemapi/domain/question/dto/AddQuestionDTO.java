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
public class AddQuestionDTO {

    private String id;
    private String title;
    private String content;
    private String userId;
    private String userName;
    private String collegeId;
    private String moduleId;
    private QuestionStatus status = QuestionStatus.PENDING;
    private List<String> tags;
}
