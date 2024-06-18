package org.semicorp.mscitemapi.domain.tag.response;

import lombok.*;
import org.semicorp.mscitemapi.domain.tag.Tag;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TagResponse {

    private Tag tag;
    private HttpStatus status;
    private String message;
}
