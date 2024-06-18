package org.semicorp.mscitemapi.domain.tag.dao;

public class QueryTag {

    static final String QUERY_FIND_ALL = "SELECT * FROM items.tag as t ORDER BY t.name;";
    static final String QUERY_FIND_ONE_BY_ID = "SELECT * FROM items.tag as t WHERE t.id =:tagId;";
    static final String QUERY_FIND_ONE_BY_NAME = "SELECT * FROM items.tag as t WHERE LOWER(t.name) = LOWER(:tagName);";
    static final String QUERY_INSERT = "INSERT INTO items.tag (id, name) VALUES (:id, :name);";
}
