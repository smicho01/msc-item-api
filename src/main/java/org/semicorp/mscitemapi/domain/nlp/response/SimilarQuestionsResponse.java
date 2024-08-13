package org.semicorp.mscitemapi.domain.nlp.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SimilarQuestionsResponse {
    private String question;
    private String questionId;
}
