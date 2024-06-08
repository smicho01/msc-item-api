package org.semicorp.mscitemapi.domain.question.dao;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.semicorp.mscitemapi.domain.question.Question;
import org.semicorp.mscitemapi.domain.question.dao.rowmappers.QuestionFullDTORowMapper;
import org.semicorp.mscitemapi.domain.question.dao.rowmappers.QuestionRowMapper;
import org.semicorp.mscitemapi.domain.question.dto.QuestionFullDTO;

import java.util.List;

public interface QuestionDAO {

//    @RegisterRowMapper(QuestionRowMapper.class)
//    @SqlQuery(QueryQuestion.QUERY_FIND_ALL)
//    List<Question> findAll();


    @RegisterRowMapper(QuestionFullDTORowMapper.class)
    @SqlQuery(QueryQuestion.QUERY_FIND_ALL)
    List<QuestionFullDTO> findAll();
}
