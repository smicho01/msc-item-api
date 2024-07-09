package org.semicorp.mscitemapi.domain.bestanswer;

import lombok.*;

import java.sql.Timestamp;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BestAnswer {

    private String questionAuthorId;
    private String answerAuthorId;
    private String questionId;
    private String answerId;
    private Timestamp timestamp;

}
