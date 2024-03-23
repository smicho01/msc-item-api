package org.semicorp.mscitemapi.item.dao;

public class QueryItem {

    static final String QUERY_FIND_ALL = "SELECT * FROM item;";
    static final String QUERY_FIND_BY_ID = "SELECT * FROM item WHERE id = :id";
    public static final String QUERY_FIND_BY_USER_ID = "SELECT * FROM item WHERE userId = :id";
}
