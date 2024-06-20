package org.semicorp.mscitemapi.domain.answer.dao;

import lombok.*;
import org.semicorp.mscitemapi.domain.answer.Answer;
import org.semicorp.mscitemapi.domain.question.ItemStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerRow {

    private String id;
    private String content;
    private String questionId;
    private String userId;
    private ItemStatus status = ItemStatus.PENDING;
    private boolean best; // is selected as best answer ?
    private LocalDateTime dateCreated;
    private LocalDateTime dateModified;

    public AnswerRow(@NonNull final Answer answer) {
        this.id = answer.getId();
        this.content = answer.getContent();
        this.questionId = answer.getQuestionId();
        this.userId = answer.getUserId();
        this.status = answer.getStatus();
        this.best = answer.isBest();
        this.dateCreated = answer.getDateCreated();
        this.dateModified = answer.getDateModified();
    }
}
