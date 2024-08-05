package org.semicorp.mscitemapi.service.malicious;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FlaggedUserLevelResponse {
    private String level;
    private int count;
}
