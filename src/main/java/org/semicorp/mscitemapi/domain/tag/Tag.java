package org.semicorp.mscitemapi.domain.tag;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Tag {

    private String id;
    private String name;

}
