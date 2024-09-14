package org.semicorp.mscitemapi.domain.bestanswer;

import org.h2.jdbcx.JdbcDataSource;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.datasource.driverClassName=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=password",
        "spring.datasource.platform=h2",
        "spring.jpa.hibernate.ddl-auto=update",
        "spring.h2.console.enabled=true"
})
class BestAnswerServiceTest {

    private BestAnswerService bestAnswerService;

    private Jdbi jdbi;

    @BeforeEach
    void setUp() {
        // Initialize H2 DataSource for Jdbi
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        dataSource.setUser("sa");
        dataSource.setPassword("password");

        // Initialize Jdbi with the H2 DataSource
        jdbi = Jdbi.create(dataSource);

        jdbi.withHandle(handle -> handle.execute("CREATE SCHEMA IF NOT EXISTS ITEMS;"));

        jdbi.withHandle(handle -> handle.execute("CREATE TABLE IF NOT EXISTS items.bestanswer (" +
                "questionAuthorId VARCHAR(255) NOT NULL , " +
                "answerAuthorId VARCHAR(255) NOT NULL , " +
                "questionId VARCHAR(255) NOT NULL , " +
                "answerId VARCHAR(255) NOT NULL , " +
                "timestamp TIMESTAMP NOT NULL " +
                ");"));

        bestAnswerService = new BestAnswerService(jdbi);
    }

    @Test
    void save_ShouldReturnTrue_WhenBestAnswerIsValid() {
        BestAnswer bestAnswer = new BestAnswer();
        bestAnswer.setQuestionAuthorId("author1");
        bestAnswer.setAnswerAuthorId("author2");
        bestAnswer.setQuestionId("q1");
        bestAnswer.setAnswerId("a1");
        bestAnswer.setTimestamp(new Timestamp(System.currentTimeMillis()));

        Boolean result = bestAnswerService.save(bestAnswer);

        assertTrue(result);
    }

    @Test
    void save_ShouldReturnFalse_WhenExceptionOccurs() {
        BestAnswer bestAnswer = new BestAnswer();
        bestAnswer.setQuestionAuthorId(null); // Can'y be null. Wi cause failure

        Boolean result = bestAnswerService.save(bestAnswer);

        assertFalse(result);
    }
}
