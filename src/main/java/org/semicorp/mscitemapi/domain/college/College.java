package org.semicorp.mscitemapi.domain.college;


import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class College {

    private String id;
    private String name;
}
