package org.semicorp.mscitemapi.item.dao;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.semicorp.mscitemapi.item.Item;

import java.util.List;

public interface ItemDAO {

    @RegisterRowMapper(ItemRowMapper.class)
    @SqlQuery(QueryItem.QUERY_FIND_BY_ID)
    Item findById(@Bind("id") String id);

    @RegisterRowMapper(ItemRowMapper.class)
    @SqlQuery(QueryItem.QUERY_FIND_ALL)
    List<Item> findAll();
}
