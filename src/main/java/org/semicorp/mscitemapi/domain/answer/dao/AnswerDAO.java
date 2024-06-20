package org.semicorp.mscitemapi.domain.answer.dao;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.semicorp.mscitemapi.domain.answer.Answer;
import org.semicorp.mscitemapi.domain.answer.dao.rowmappers.AnswerRowMapper;

import java.util.List;

public interface AnswerDAO {

    @RegisterRowMapper(AnswerRowMapper.class)
    @SqlQuery(QueryAnswer.QUERY_FIND_ALL_FOR_QUESTION_ID)
    List<Answer> findAllByQuestionId(@Bind("questionId") String questionId);

    @RegisterRowMapper(AnswerRowMapper.class)
    @SqlQuery(QueryAnswer.QUERY_FIND_ALL_FOR_QUESTION_ID_WITH_STATUS)
    List<Answer> findAllByQuestionIdAndStatus(@Bind("questionId") String questionId,
                                              @Bind("status") String status);

    @RegisterRowMapper(AnswerRowMapper.class)
    @SqlQuery(QueryAnswer.QUERY_FIND_ALL_ACTIVE_AND_ALL_FOR_USER_ID_ALL_STATUSES)
    List<Answer> findAllActiveAndAllStatusesForUserId(@Bind("questionId") String questionId,
                                              @Bind("userId") String userId);

    @SqlUpdate(QueryAnswer.QUERY_INSERT_ANSWER)
    boolean insert(@BindBean final AnswerRow questionRow);

}
