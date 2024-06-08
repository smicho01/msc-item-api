package org.semicorp.mscitemapi.domain.question.dto;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QuestionFullDTO {

    private String id;
    private String title;
    private String content;
    private String userId;
    private String userName;
    private String collegeId;
    private String collegeName;
    private String moduleId;
    private String moduleName;
}
