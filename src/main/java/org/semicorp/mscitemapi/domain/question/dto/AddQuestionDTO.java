package org.semicorp.mscitemapi.domain.question.dto;

import lombok.*;
import org.semicorp.mscitemapi.domain.question.ItemStatus;

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
    private ItemStatus status = ItemStatus.PENDING;
    private List<String> tags;
}
