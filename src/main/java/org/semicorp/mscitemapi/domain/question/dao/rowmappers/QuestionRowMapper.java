package org.semicorp.mscitemapi.domain.question.dao.rowmappers;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.semicorp.mscitemapi.domain.question.Question;
import org.semicorp.mscitemapi.domain.question.dao.QuestionDAO;
import org.semicorp.mscitemapi.domain.question.dao.QuestionRow;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionRowMapper implements RowMapper<Question> {

    @Override
    public Question map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new Question(
                rs.getString("id"),
                rs.getString("title"),
                rs.getString("content"),
                rs.getString("userId"),
                rs.getString("userName"),
                rs.getString("collegeId"),
                rs.getString("moduleId")
        );
    }
}
