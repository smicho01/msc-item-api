CREATE TABLE IF NOT EXISTS items.module (
    id VARCHAR(36)  NOT NULL,
    name VARCHAR(120) NOT NULL,
    collegeId VARCHAR(36),
    PRIMARY KEY (id, name),
    FOREIGN KEY (collegeId) REFERENCES items.college(id)
);
