package org.semicorp.mscitemapi.domain.modules.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.semicorp.mscitemapi.domain.modules.Module;

@Getter
@Setter
@ToString
public class CollegeRow {
    private String id;
    private String name;

    public CollegeRow(@NotNull final Module college) {
        this.id = college.getId();
        this.name = college.getName();
    }
}
