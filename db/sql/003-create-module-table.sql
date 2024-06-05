CREATE TABLE IF NOT EXISTS items.module (
    id VARCHAR(36)  NOT NULL,
    name VARCHAR(120) NOT NULL UNIQUE,
    collegeId VARCHAR(36),
    PRIMARY KEY (id),
    FOREIGN KEY (collegeId) REFERENCES items.college(id)
);
