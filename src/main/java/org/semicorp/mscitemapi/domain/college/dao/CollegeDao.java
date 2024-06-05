package org.semicorp.mscitemapi.domain.college.dao;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.semicorp.mscitemapi.domain.college.College;

public interface CollegeDao {

    @SqlUpdate(QueryCollege.QUERY_INSERT_COLLEGE)
    boolean insert(@BindBean final CollegeRow collegeRow);

    @RegisterRowMapper(CollegeRowMapper.class)
    @SqlQuery(QueryCollege.QUERY_FIND_BY_NAME)
    College findByName(@Bind("name") String name);

    @RegisterRowMapper(CollegeRowMapper.class)
    @SqlQuery(QueryCollege.QUERY_FIND_BY_ID)
    College findById(@Bind("id") String id);
}
