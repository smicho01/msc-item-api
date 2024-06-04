package org.semicorp.mscitemapi.domain.college.dao;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.semicorp.mscitemapi.domain.college.College;

@Getter
@Setter
public class CollegeRow {
    private String id;
    private String name;

    public CollegeRow(@NotNull final College college) {
        this.id = college.getId();
        this.name = college.getName();
    }
}
