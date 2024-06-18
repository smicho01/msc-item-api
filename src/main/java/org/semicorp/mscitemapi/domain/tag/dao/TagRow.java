package org.semicorp.mscitemapi.domain.tag.dao;

import lombok.*;
import org.semicorp.mscitemapi.domain.tag.Tag;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TagRow {

    private String id;
    private String name;

    public TagRow(@NonNull final Tag tag) {
        this.id = tag.getId();
        this.name = tag.getName();
    }
}
