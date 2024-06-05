package org.semicorp.mscitemapi.domain.modules.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.semicorp.mscitemapi.domain.modules.Module;

@Getter
@Setter
@ToString
public class ModuleRow {
    private String id;
    private String name;
    private String collegeId;
    private String collegeName;

    public ModuleRow(@NotNull final Module college) {
        this.id = college.getId();
        this.name = college.getName();
        this.collegeId = college.getCollegeId();
        this.collegeName = college.getCollegeName();
    }
}
