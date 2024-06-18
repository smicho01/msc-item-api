package org.semicorp.mscitemapi.domain.tag.dao.tag;

import lombok.*;
import org.semicorp.mscitemapi.domain.tag.TagQuestion;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TagQuestionRow {

    private String tagId;
    private String questionId;

    public TagQuestionRow(@NonNull final TagQuestion tagQuestion) {
        this.tagId = tagQuestion.getTagId();
        this.questionId = tagQuestion.getQuestionId();
    }

}
