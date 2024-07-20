
-- CREATE SCHEMA IF NOT EXISTS items;

CREATE TABLE IF NOT EXISTS items.question (
    id VARCHAR(36)  PRIMARY KEY,
    title VARCHAR(250) NOT NULL,
    content TEXT NOT NULL,
    userId VARCHAR(36) NOT NULL,
    userName VARCHAR(150) NOT NULL,
    collegeId VARCHAR(36) NOT NULL,
    moduleId VARCHAR(36) NOT NULL,
    status VARCHAR(40) NOT NULL,
    dateCreated timestamp default now(),
    dateModified timestamp default now(),
    hash VARCHAR(128) DEFAULT 'default_hash_value' NOT NULL
);
CREATE INDEX idx_question_title ON items.question(title);
CREATE INDEX idx_question_username ON items.question(userName);
CREATE INDEX idx_question_hash ON items.question(hash);
