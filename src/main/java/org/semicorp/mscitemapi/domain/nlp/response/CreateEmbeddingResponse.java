package org.semicorp.mscitemapi.domain.nlp.response;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CreateEmbeddingResponse {
    private String mongoId;
    private String question;
    private String questionId;
    private List<Double> question_embedding;
}
