package org.semicorp.mscitemapi.domain.question.dao;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.semicorp.mscitemapi.domain.question.Question;
import org.semicorp.mscitemapi.domain.question.dao.rowmappers.QuestionFullDTORowMapper;
import org.semicorp.mscitemapi.domain.question.dao.rowmappers.QuestionRowMapper;
import org.semicorp.mscitemapi.domain.question.dto.QuestionFullDTO;

import java.util.List;

public interface QuestionDAO {

    @RegisterRowMapper(QuestionFullDTORowMapper.class)
    @SqlQuery(QueryQuestion.QUERY_FIND_ALL)
    List<QuestionFullDTO> findAll();

    @SqlUpdate(QueryQuestion.QUERY_INSERT_QUESTION)
    boolean insert(@BindBean final QuestionRow questionRow);
}
