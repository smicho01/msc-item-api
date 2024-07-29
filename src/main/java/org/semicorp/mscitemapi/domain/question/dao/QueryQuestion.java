package org.semicorp.mscitemapi.domain.question.dao;

public class QueryQuestion {

    static final String QUERY_FIND_ONE = "SELECT q.* , m.name as moduleName, c.name as collegeName " +
            "FROM items.question as q , items.college as c, items.module as m " +
            "WHERE q.collegeId = c.id AND q.moduleId = m.id AND q.id =:questionId";

    static final String QUERY_FIND_ALL = "SELECT q.* , m.name as moduleName, c.name as collegeName " +
            "FROM items.question as q , items.college as c, items.module as m " +
            "WHERE q.collegeId = c.id AND q.moduleId = m.id " +
            "ORDER BY dateCreated DESC LIMIT :limit";

    public static final String QUERY_FIND_BY_USERID = "SELECT q.*, " +
            "m.name as moduleName, c.name as collegeName " +
            "FROM items.question as q , items.college as c, items.module as m " +
            "WHERE q.collegeId = c.id AND q.moduleId = m.id AND q.userid =:userId " +
            "ORDER BY datecreated DESC";

    public static final String QUERY_FIND_BY_TITLE_LIKE = "SELECT q.* , m.name as moduleName, c.name as collegeName " +
            "FROM items.question as q , items.college as c, items.module as m " +
            "WHERE q.collegeId = c.id AND q.moduleId = m.id " +
            "AND q.status = 'ACTIVE' " +
            "AND LOWER(q.title) LIKE LOWER(:phrase) " +
            "ORDER BY q.datecreated DESC;";

    public static final String QUERY_FIND_BY_USERID_SHORT = "SELECT q.*, m.name as moduleName, c.name as collegeName, COUNT(a.id) AS answersCount\n" +
            "FROM  items.question q " +
            "JOIN  items.module m ON q.moduleid = m.id " +
            "JOIN  items.college c ON q.collegeid = c.id " +
            "LEFT JOIN  items.answer a ON q.id = a.questionId " +
            "WHERE  LOWER(q.status) = LOWER(:status) " +
            "GROUP BY q.id, m.name, c.name " +
            "ORDER BY q.dateCreated DESC " +
            "LIMIT :limit;";



    static final String QUERY_FIND_ALL_SHORT = "SELECT q.id, q.title, LEFT(q.content, 100) as content, q.userid, q.username, q.collegeid, q.moduleid, q.status, q.hash " +
            "m.name as moduleName, c.name as collegeName, q.dateCreated, q.dateModified " +
            "FROM items.question as q , items.college as c, items.module as m " +
            "WHERE q.collegeId = c.id AND q.moduleId = m.id";

    static final String QUERY_INSERT_QUESTION = "INSERT INTO items.question (id, title, content, userId, userName, collegeId, moduleId, status, hash) " +
            "VALUES(:id, :title, :content, :userId, :userName, :collegeId, :moduleId, :status, :hash);";


    static final String QUERY_FIND_QUESTIONS_BY_TAG_ID = "SELECT q.*, m.name as moduleName, c.name as collegeName " +
            "FROM items.question as q, items.tag_question as tq, items.college as c, items.module as m " +
            "WHERE q.id = tq.question_id " +
            "AND q.collegeId = c.id  " +
            "AND q.moduleId = m.id " +
            "AND tq.tag_id = :tagId;";

    static final String QUERY_FIND_QUESTIONS_BY_TAG_NAME = "SELECT q.*, m.name as moduleName, c.name as collegeName\n" +
            "FROM items.question as q, items.tag_question as tq, items.college as c, items.module as m, items.tag as t\n" +
            "WHERE q.id = tq.question_id " +
            "AND q.collegeId = c.id " +
            "AND q.moduleId = m.id " +
            "AND t.id = tq.tag_id " +
            "AND LOWER(t.name) = LOWER(:tagName);";
}


