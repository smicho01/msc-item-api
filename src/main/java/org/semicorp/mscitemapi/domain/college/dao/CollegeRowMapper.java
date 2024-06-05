package org.semicorp.mscitemapi.domain.college.dao;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.semicorp.mscitemapi.domain.college.College;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CollegeRowMapper implements RowMapper<College> {

    @Override
    public College map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new College(
                rs.getString("id"),
                rs.getString("name")
        );
    }
}
