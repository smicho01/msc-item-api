package org.semicorp.mscitemapi.domain.tag.dao.tag;

public class QueryTag {

    static final String QUERY_FIND_ALL = "SELECT * FROM items.tag as t ORDER BY t.name;";
    static final String QUERY_FIND_ONE_BY_ID = "SELECT * FROM items.tag as t WHERE t.id =:tagId;";
    static final String QUERY_FIND_ONE_BY_NAME = "SELECT * FROM items.tag as t WHERE LOWER(t.name) = LOWER(:tagName);";
    static final String QUERY_INSERT = "INSERT INTO items.tag (id, name) VALUES (:id, :name);";

    static final String GET_TAGS_FOR_QUESTION_ID = "SELECT t.name, t.id\n" +
            "FROM items.tag as t, items.tag_question as tq\n" +
            "WHERE t.id = tq.tag_id\n" +
            "AND tq.question_id = :questionId;";
}
