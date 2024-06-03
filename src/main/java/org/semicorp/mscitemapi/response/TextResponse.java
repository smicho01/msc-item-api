package org.semicorp.mscitemapi.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TextResponse implements BasicResponse {
    private String response;
    private int code;
}
