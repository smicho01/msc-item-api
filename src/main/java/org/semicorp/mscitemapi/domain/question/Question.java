package org.semicorp.mscitemapi.domain.question;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    private String id;
    private String title;
    private String content;
}
