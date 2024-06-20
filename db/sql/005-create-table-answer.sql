CREATE TABLE IF NOT EXISTS items.answer (
    id VARCHAR(36)  PRIMARY KEY,
    content TEXT NOT NULL,
    userId VARCHAR(36) NOT NULL,
    questionId VARCHAR(36) NOT NULL,
    userName VARCHAR(100) NOT NULL,
    dateCreated timestamp default now(),
    dateModified timestamp default now(),
    status VARCHAR(40) NOT NULL,
    best BOOLEAN NOT NULL DEFAULT FALSE
);
