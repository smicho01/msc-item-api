package org.semicorp.mscitemapi.domain.modules.dao;

public class QueryModule {

    public static final String QUERY_FIND_ALL = "SELECT m.id, m.name, m.collegeId, c.name as college_name\n" +
            "FROM items.module as m, items.college as c\n" +
            "WHERE m.collegeId = c.id\n" +
            "ORDER BY m.name, c.name;";

    public static final String QUERY_FIND_MODULE_BY_ID = "SELECT m.id, m.name, m.collegeId, c.name as college_name\n" +
            "FROM items.module as m, items.college as c\n" +
            "WHERE m.collegeId = c.id AND m.id = :id;";



    static final String QUERY_INSERT_MODULE = "INSERT INTO items.module (id, name, collegeId) VALUES(:id, :name, :collegeId);";


}
