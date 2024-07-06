package org.semicorp.mscitemapi.domain.blockchain;

import lombok.*;
import org.semicorp.mscitemapi.domain.answer.Answer;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BlockchainMsgBestAnswer {

    private String messageId;
    private String answerId;
    private String questionId;
    private String userId;


    public BlockchainMsgBestAnswer(UUID messageUUID, String answerId, String questionId, String userId) {
        this.messageId = messageId.toString();
        this.answerId = answerId;
        this.questionId = answerId;
        this.userId = userId;
    }
}
