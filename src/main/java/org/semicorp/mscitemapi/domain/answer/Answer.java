package org.semicorp.mscitemapi.domain.answer;

import lombok.*;
import org.semicorp.mscitemapi.domain.question.ItemStatus;

import java.time.LocalDateTime;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Answer {

    private String id;
    private String content;
    private String questionId;
    private String userId;
    private String userName;
    private ItemStatus status;
    private boolean best; // is selected as best answer ?
    private LocalDateTime dateCreated;
    private LocalDateTime dateModified;
    private String hash;
}