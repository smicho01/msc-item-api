package org.semicorp.mscitemapi.item.dao;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.semicorp.mscitemapi.item.Item;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemRowMapper implements RowMapper<Item> {

    @Override
    public Item map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new Item(
                rs.getString("id"),
                rs.getString("name"),
                rs.getString("descr"),
                rs.getString("ownerId")
        );
    }
}
