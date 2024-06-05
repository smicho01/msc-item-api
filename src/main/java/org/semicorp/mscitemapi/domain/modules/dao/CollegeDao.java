package org.semicorp.mscitemapi.domain.modules.dao;

import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface CollegeDao {

    @SqlUpdate(QueryCollege.QUERY_INSERT_COLLEGE)
    boolean insert(@BindBean final CollegeRow collegeRow);
}
