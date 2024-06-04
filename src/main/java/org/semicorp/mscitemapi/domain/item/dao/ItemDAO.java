package org.semicorp.mscitemapi.domain.item.dao;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.semicorp.mscitemapi.domain.item.Item;

import java.util.List;

public interface ItemDAO {

    @RegisterRowMapper(ItemRowMapper.class)
    @SqlQuery(QueryItem.QUERY_FIND_ALL)
    List<Item> findAll();

    @RegisterRowMapper(ItemRowMapper.class)
    @SqlQuery(QueryItem.QUERY_FIND_BY_ID)
    Item findById(@Bind("id") String id);

    @RegisterRowMapper(ItemRowMapper.class)
    @SqlQuery(QueryItem.QUERY_FIND_BY_USER_ID)
    List<Item> findAllByUserId(@Bind("id") String userId);
}
