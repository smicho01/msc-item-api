package org.semicorp.mscitemapi.domain.blockchain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
        this.messageId = messageUUID.toString();
        this.answerId = answerId;
        this.questionId = questionId;
        this.userId = userId;
    }
}
