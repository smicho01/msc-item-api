package org.semicorp.mscitemapi.domain.tag.rowmappers;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.semicorp.mscitemapi.domain.tag.TagQuestion;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagQuestionRowMapper implements RowMapper<TagQuestion> {
    @Override
    public TagQuestion map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new TagQuestion(
                rs.getString("tag_id"),
                rs.getString("question_id")
        );
    }
}
