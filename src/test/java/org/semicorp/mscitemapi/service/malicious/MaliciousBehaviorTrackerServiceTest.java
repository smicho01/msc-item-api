package org.semicorp.mscitemapi.service.malicious;

import org.h2.jdbcx.JdbcDataSource;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MaliciousBehaviorTrackerServiceTest {

    private Jdbi jdbi;
    private MaliciousBehaviorTrackerService maliciousBehaviorTrackerService;

    @BeforeEach
    void setUp() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        dataSource.setUser("sa");
        dataSource.setPassword("");

        jdbi = Jdbi.create(dataSource);
        maliciousBehaviorTrackerService = new MaliciousBehaviorTrackerService(jdbi);

        jdbi.useHandle(handle -> {
            handle.execute("CREATE SCHEMA IF NOT EXISTS items");
            handle.execute("CREATE TABLE IF NOT EXISTS items.bestanswer (" +
                    "questionAuthorId VARCHAR(255), " +
                    "answerAuthorId VARCHAR(255), " +
                    "questionId VARCHAR(255), " +
                    "answerId VARCHAR(255), " +
                    "timestamp TIMESTAMP)");

            handle.execute("CREATE TABLE IF NOT EXISTS items.flaggedusers (" +
                    "questionOwner VARCHAR(255), " +
                    "answerOwner VARCHAR(255), " +
                    "level VARCHAR(255), " +
                    "reason VARCHAR(255))");
        });
    }

    @Test
    void testCheckForRepetitiveBehaviour_NotMalicious() {
        jdbi.useHandle(handle -> {
            handle.execute("INSERT INTO items.bestanswer (questionAuthorId, answerAuthorId, questionId, answerId, timestamp) " +
                    "VALUES ('user1', 'user2', 'q1', 'a1', ?)", Timestamp.valueOf("2023-09-14 10:10:10"));
        });

        Boolean result = maliciousBehaviorTrackerService.checkForRepetitiveBehaviour("user1", "user2");
        assertTrue(result, "The behavior should not be flagged as malicious");
    }

    @Test
    void testCheckForRepetitiveBehaviour_Malicious() {
        jdbi.useHandle(handle -> {
            handle.execute("INSERT INTO items.bestanswer (questionAuthorId, answerAuthorId, questionId, answerId, timestamp) " +
                            "VALUES ('user1', 'user2', 'q1', 'a1', ?)", Timestamp.valueOf("2023-09-14 10:10:10"),
                    "('user1', 'user2', 'q2', 'a2', ?)", Timestamp.valueOf("2023-09-14 10:10:10"),
                    "('user1', 'user2', 'q3', 'a3', ?)", Timestamp.valueOf("2023-09-14 10:11:10"),
                    "('user1', 'user2', 'q4', 'a4', ?)", Timestamp.valueOf("2023-09-14 10:12:10"),
                    "('user1', 'user2', 'q5', 'a5', ?)", Timestamp.valueOf("2023-09-14 10:13:10"),
                    "('user1', 'user2', 'q6', 'a6', ?)", Timestamp.valueOf("2023-09-14 10:14:10")

            );
        });

        Boolean result = maliciousBehaviorTrackerService.checkForRepetitiveBehaviour("user1", "user2");
        assertTrue(result, "The behavior should be flagged as malicious");
    }

}
