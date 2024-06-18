package org.semicorp.mscitemapi.domain.tag.rowmappers;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.semicorp.mscitemapi.domain.tag.Tag;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagRowMapper implements RowMapper<Tag> {
    @Override
    public Tag map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new Tag(
                rs.getString("id"),
                rs.getString("name")
        );
    }
}
