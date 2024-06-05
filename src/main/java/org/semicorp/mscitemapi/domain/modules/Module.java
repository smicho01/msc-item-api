package org.semicorp.mscitemapi.domain.modules;


import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Module {

    private String id;
    private String name;
    private String collegeId;
    private String collegeName;
}
