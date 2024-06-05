package org.semicorp.mscitemapi.domain.college.dao;

public class QueryCollege {

    static final String QUERY_INSERT_COLLEGE = "INSERT INTO items.college (id, name) VALUES(:id, :name);";
    static final String  QUERY_FIND_BY_NAME = "SELECT * FROM items.college WHERE name =:name;";
    static final String  QUERY_FIND_BY_ID = "SELECT * FROM items.college WHERE id =:id;";

}
