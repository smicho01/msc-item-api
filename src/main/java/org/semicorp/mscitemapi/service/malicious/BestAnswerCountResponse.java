package org.semicorp.mscitemapi.service.malicious;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class BestAnswerCountResponse {
    private String questionauthorid;
    private String answerauthorid;
    private Integer count;
}
