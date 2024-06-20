package org.semicorp.mscitemapi.domain.question.dao.rowmappers;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.semicorp.mscitemapi.domain.question.ItemStatus;
import org.semicorp.mscitemapi.domain.question.dto.QuestionFullDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionFullDTORowMapper implements RowMapper<QuestionFullDTO> {
    @Override
    public QuestionFullDTO map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new QuestionFullDTO(
                rs.getString("id"),
                rs.getString("title"),
                rs.getString("content"),
                rs.getString("userId"),
                rs.getString("userName"),
                rs.getString("collegeId"),
                rs.getString("collegeName"),
                rs.getString("moduleId"),
                rs.getString("moduleName"),
                ItemStatus.valueOf(rs.getString("status")),
                rs.getTimestamp("dateCreated").toLocalDateTime(),
                rs.getTimestamp("dateModified").toLocalDateTime()
        );
    }

}
