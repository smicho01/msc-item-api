package org.semicorp.mscitemapi.domain.question.dao;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
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

    @RegisterRowMapper(QuestionFullDTORowMapper.class)
    @SqlQuery(QueryQuestion.QUERY_FIND_ALL_SHORT)
    List<QuestionFullDTO> findAllShort();

    @RegisterRowMapper(QuestionFullDTORowMapper.class)
    @SqlQuery(QueryQuestion.QUERY_FIND_BY_USERID)
    List<QuestionFullDTO> findAllByUserId(@Bind("userId") String userId);

    @RegisterRowMapper(QuestionFullDTORowMapper.class)
    @SqlQuery(QueryQuestion.QUERY_FIND_BY_USERID_SHORT)
    List<QuestionFullDTO> findAllByUserIdShort(@Bind("userId") String userId);

    @RegisterRowMapper(QuestionFullDTORowMapper.class)
    @SqlQuery(QueryQuestion.QUERY_FIND_QUESTIONS_BY_TAG_ID)
    List<QuestionFullDTO> findQuestionsByTagId(@Bind("tagId") String tagId);

    @SqlUpdate(QueryQuestion.QUERY_INSERT_QUESTION)
    boolean insert(@BindBean final QuestionRow questionRow);
}
