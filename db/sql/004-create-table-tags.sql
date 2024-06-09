CREATE TABLE IF NOT EXISTS items.tag (
    id VARCHAR(36)  NOT NULL,
    name VARCHAR(50) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS items.tag_question (
    tag_id VARCHAR(36)  NOT NULL,
    question_id VARCHAR(36)  NOT NULL,
    PRIMARY KEY (tag_id, question_id)
);