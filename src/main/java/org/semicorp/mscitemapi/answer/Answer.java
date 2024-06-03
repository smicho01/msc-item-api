package org.semicorp.mscitemapi.answer;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Answer {

    private String id;
    private String questionId;
    private String userId;
    private String content;
}
