package org.semicorp.mscitemapi.domain.question.dao;

public class QueryQuestion {

    static final String QUERY_FIND_ALL = "SELECT q.* , m.name as moduleName, c.name as collegeName\n" +
            "FROM items.question as q , items.college as c, items.module as m\n" +
            "WHERE q.collegeId = c.id AND q.moduleId = m.id";

    static final String QUERY_INSERT_QUESTION = "INSERT INTO items.question (id, title, content, userId, userName, collegeId, moduleId, status) " +
            "VALUES(:id, :title, :content, :userId, :userName, :collegeId, :moduleId, :status);";

}
