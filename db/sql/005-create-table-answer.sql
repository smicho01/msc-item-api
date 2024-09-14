CREATE TABLE IF NOT EXISTS items.answer (
    id VARCHAR(36)  PRIMARY KEY,
    content TEXT NOT NULL,
    userId VARCHAR(36) NOT NULL,
    questionId VARCHAR(36) NOT NULL,
    userName VARCHAR(100) NOT NULL,
    dateCreated timestamp default now(),
    dateModified timestamp default now(),
    status VARCHAR(40) NOT NULL,
    best BOOLEAN NOT NULL DEFAULT FALSE,
    hash VARCHAR(128) DEFAULT 'default_hash_value' NOT NULL
);

CREATE INDEX idx_answer_userid ON items.answer(userId);
CREATE INDEX idx_answer_questionid ON items.answer(questionId);
CREATE INDEX idx_answer_hash ON items.answer(hash);

CREATE TABLE IF NOT EXISTS items.bestanswer (
    questionAuthorId VARCHAR(36),
    answerAuthorId VARCHAR(36),
    questionId VARCHAR(36),
    answerId VARCHAR(36),
    timestamp timestamp NOT NULL,
    PRIMARY KEY (questionId, answerId)
);
CREATE INDEX idx_bestanswer_questionauthorid ON items.bestanswer(questionAuthorId);
CREATE INDEX idx_bestanswer_answerauthorid ON items.bestanswer(answerAuthorId);


ALTER TABLE items.bestanswer
ADD CONSTRAINT fk_bestanswer_answer
FOREIGN KEY (answerId) REFERENCES items.answer(id)
ON DELETE CASCADE
ON UPDATE CASCADE;

ALTER TABLE items.bestanswer
ADD CONSTRAINT fk_bestanswer_question
FOREIGN KEY (questionId) REFERENCES items.question(id)
ON DELETE CASCADE
ON UPDATE CASCADE;