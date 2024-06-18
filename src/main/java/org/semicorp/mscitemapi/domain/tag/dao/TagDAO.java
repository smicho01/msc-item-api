package org.semicorp.mscitemapi.domain.tag.dao;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.semicorp.mscitemapi.domain.question.dao.QueryQuestion;
import org.semicorp.mscitemapi.domain.question.dao.QuestionRow;
import org.semicorp.mscitemapi.domain.tag.Tag;
import org.semicorp.mscitemapi.domain.tag.rowmappers.TagRowMapper;

import java.util.List;

public interface TagDAO {

    @RegisterRowMapper(TagRowMapper.class)
    @SqlQuery(QueryTag.QUERY_FIND_ALL)
    List<Tag> findAll();

    @RegisterRowMapper(TagRowMapper.class)
    @SqlQuery(QueryTag.QUERY_FIND_ONE_BY_ID)
    Tag findById(@Bind("tagId") String tagId);

    @RegisterRowMapper(TagRowMapper.class)
    @SqlQuery(QueryTag.QUERY_FIND_ONE_BY_NAME)
    Tag findByName(@Bind("tagName") String tagName);

    @SqlUpdate(QueryTag.QUERY_INSERT)
    boolean insert(@BindBean final TagRow tagRow);

}
