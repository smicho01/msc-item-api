package org.semicorp.mscitemapi.item;


import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Item implements Serializable {

    private String id;
    private String name;
    private String descr;
    private String ownerId;

}
