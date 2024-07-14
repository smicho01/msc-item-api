package org.semicorp.mscitemapi.domain.answer.dao.rowmappers;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.semicorp.mscitemapi.domain.answer.Answer;
import org.semicorp.mscitemapi.domain.question.ItemStatus;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AnswerRowMapper implements RowMapper<Answer> {
    @Override
    public Answer map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new Answer(
                rs.getString("id"),
                rs.getString("content"),
                rs.getString("questionId"),
                rs.getString("userId"),
                rs.getString("userName"),
                ItemStatus.valueOf(rs.getString("status")),
                rs.getBoolean("best"),
                rs.getTimestamp("dateCreated").toLocalDateTime(),
                rs.getTimestamp("dateModified").toLocalDateTime(),
                rs.getString("hash")
        );
    }
}
