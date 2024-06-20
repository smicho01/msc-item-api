package org.semicorp.mscitemapi.domain.question.dao;


import lombok.*;
import org.semicorp.mscitemapi.domain.question.Question;
import org.semicorp.mscitemapi.domain.question.ItemStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionRow {

    private String id;
    private String title;
    private String content;
    private String userId;
    private String userName;
    private String collegeId;
    private String moduleId;
    private ItemStatus status;
    private LocalDateTime dateCreated;
    private LocalDateTime dateModified;

    public QuestionRow(@NonNull final Question question) {
        this.id = question.getId();
        this.title = question.getTitle();
        this.content = question.getContent();
        this.userId = question.getUserId();
        this.userName = question.getUserName();
        this.collegeId = question.getCollegeId();
        this.moduleId = question.getModuleId();
        this.status = question.getStatus();
        this.dateCreated = question.getDateCreated();
        this.dateModified = question.getDateModified();
    }

}
