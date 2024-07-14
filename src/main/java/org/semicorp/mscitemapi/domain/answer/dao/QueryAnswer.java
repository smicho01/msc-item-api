package org.semicorp.mscitemapi.domain.answer.dao;

public class QueryAnswer {


    public static final String QUERY_FIND_BY_ID = "SELECT * from items.answer WHERE id =:id;";
    static final String QUERY_FIND_ALL = "SELECT * from items.answer;";

    public static final String QUERY_FIND_ALL_WITH_STATUS = "SELECT * from items.answer as a \n" +
            "WHERE a.status = :status\n" +
            "ORDER BY a.dateCreated DESC";

    static final String QUERY_FIND_ALL_FOR_QUESTION_ID = "SELECT * from items.answer as a \n" +
            "WHERE a.questionId = :questionId\n" +
            "ORDER BY a.dateCreated DESC;";

    static final String QUERY_FIND_ALL_FOR_QUESTION_ID_WITH_STATUS = "SELECT * from items.answer as a \n" +
            "WHERE a.questionId = :questionId\n" +
            "AND LOWER(a.status) = LOWER(:status)\n" +
            "ORDER BY a.dateCreated DESC;";

    static final String QUERY_INSERT_ANSWER = "INSERT INTO items.answer (id, content, questionId, userId, userName, status, hash) " +
            "VALUES(:id, :content, :questionId, :userId, :userName, :status, :hash);";

    static final String QUERY_FIND_ALL_ACTIVE_AND_ALL_FOR_USER_ID_ALL_STATUSES = "SELECT *\n" +
            "FROM items.answer a\n" +
            "WHERE (a.status = 'ACTIVE' AND a.questionId = :questionId)\n" +
            "   OR (a.userId = :userId AND a.questionId = :questionId)\n" +
            "ORDER BY a.dateCreated DESC;";
}
