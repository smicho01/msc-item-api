package org.semicorp.mscitemapi.domain.question.dao;

public class QueryQuestion {

    static final String QUERY_FIND_ONE = "SELECT q.* , m.name as moduleName, c.name as collegeName\n" +
            "FROM items.question as q , items.college as c, items.module as m\n" +
            "WHERE q.collegeId = c.id AND q.moduleId = m.id AND q.id =:questionId";

    static final String QUERY_FIND_ALL = "SELECT q.* , m.name as moduleName, c.name as collegeName\n" +
            "FROM items.question as q , items.college as c, items.module as m\n" +
            "WHERE q.collegeId = c.id AND q.moduleId = m.id";

    public static final String QUERY_FIND_BY_USERID = "SELECT q.*,\n" +
            "\t m.name as moduleName, c.name as collegeName \n" +
            "FROM items.question as q , items.college as c, items.module as m\n" +
            "WHERE q.collegeId = c.id AND q.moduleId = m.id AND q.userid =:userId";

    public static final String QUERY_FIND_BY_USERID_SHORT = "SELECT q.id, q.title, LEFT(q.content, 100) as content, q.userid, q.username, q.collegeid, q.moduleid, q.status,\n" +
            "\t m.name as moduleName, c.name as collegeName \n" +
            "FROM items.question as q , items.college as c, items.module as m\n" +
            "WHERE q.collegeId = c.id AND q.moduleId = m.id AND q.userid =:userId";

    static final String QUERY_FIND_ALL_SHORT = "SELECT q.id, q.title, LEFT(q.content, 100) as content, q.userid, q.username, q.collegeid, q.moduleid, q.status,\n" +
            "\t m.name as moduleName, c.name as collegeName \n" +
            "FROM items.question as q , items.college as c, items.module as m\n" +
            "WHERE q.collegeId = c.id AND q.moduleId = m.id";

    static final String QUERY_INSERT_QUESTION = "INSERT INTO items.question (id, title, content, userId, userName, collegeId, moduleId, status) " +
            "VALUES(:id, :title, :content, :userId, :userName, :collegeId, :moduleId, :status);";


    static final String QUERY_FIND_QUESTIONS_BY_TAG_ID = "SELECT q.*, m.name as moduleName, c.name as collegeName\n" +
            "FROM items.question as q, items.tag_question as tq, items.college as c, items.module as m\n" +
            "WHERE q.id = tq.question_id\n" +
            "AND q.collegeId = c.id \n" +
            "AND q.moduleId = m.id\n" +
            "AND tq.tag_id = :tagId;";
}


