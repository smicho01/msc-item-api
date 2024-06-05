package org.semicorp.mscitemapi.domain.modules.dao;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.semicorp.mscitemapi.domain.modules.Module;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ModuleRowMapper implements RowMapper<Module> {

    @Override
    public Module map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new Module(
                rs.getString("id"),
                rs.getString("name"),
                rs.getString("collegeId"),
                rs.getString("college_name")
        );
    }
}
