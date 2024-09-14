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

ALTER TABLE items.tag_question
ADD CONSTRAINT fk_tag_question
FOREIGN KEY (question_id) REFERENCES items.question(id)
ON DELETE CASCADE
ON UPDATE CASCADE;

ALTER TABLE items.tag_question
ADD CONSTRAINT fk_tag_tag
FOREIGN KEY (tag_id) REFERENCES items.tag(id)
ON DELETE CASCADE
ON UPDATE CASCADE;