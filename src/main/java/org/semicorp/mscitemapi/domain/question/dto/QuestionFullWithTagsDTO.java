package org.semicorp.mscitemapi.domain.question.dto;

import lombok.*;
import org.semicorp.mscitemapi.domain.question.ItemStatus;
import org.semicorp.mscitemapi.domain.tag.Tag;

import java.time.LocalDateTime;
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
    private ItemStatus status;
    private LocalDateTime dateCreated;
    private LocalDateTime dateModified;
    private List<Tag> tags;
}