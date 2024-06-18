package org.semicorp.mscitemapi.domain.tag;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TagQuestion {

    private String tagId;
    private String questionId;
}
