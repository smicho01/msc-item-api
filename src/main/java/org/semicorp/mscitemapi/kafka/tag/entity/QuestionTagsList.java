package org.semicorp.mscitemapi.kafka.tag.entity;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class QuestionTagsList {

    private String questionId;
    private List<String> tags;
}
