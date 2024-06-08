package org.semicorp.mscitemapi.domain.question;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Question {

    private String id;
    private String title;
    private String content;
    private String userId;
    private String userName;
    private String collegeId;
    private String moduleId;
}
