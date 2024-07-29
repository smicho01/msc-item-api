package org.semicorp.mscitemapi.domain.question.dao;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.semicorp.mscitemapi.domain.question.dao.rowmappers.QuestionFullDTORowMapper;
import org.semicorp.mscitemapi.domain.question.dto.QuestionFullDTO;

import java.util.List;

public interface QuestionDAO {

    @RegisterRowMapper(QuestionFullDTORowMapper.class)
    @SqlQuery(QueryQuestion.QUERY_FIND_ALL)
    List<QuestionFullDTO> findAll(@Bind("limit") Integer limit);

    @RegisterRowMapper(QuestionFullDTORowMapper.class)
    @SqlQuery(QueryQuestion.QUERY_FIND_ONE)
    QuestionFullDTO findById(@Bind("questionId") String questionId);

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

    @RegisterRowMapper(QuestionFullDTORowMapper.class)
    @SqlQuery(QueryQuestion.QUERY_FIND_QUESTIONS_BY_TAG_NAME)
    List<QuestionFullDTO> findQuestionsByTagName(@Bind("tagName") String tagName);

    @SqlUpdate(QueryQuestion.QUERY_INSERT_QUESTION)
    boolean insert(@BindBean final QuestionRow questionRow);

    @RegisterRowMapper(QuestionFullDTORowMapper.class)
    @SqlQuery(QueryQuestion.QUERY_FIND_BY_TITLE_LIKE)
    List<QuestionFullDTO> findByTitleLIKE(@Bind("phrase") String phrase);
}
