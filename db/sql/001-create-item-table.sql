
CREATE SCHEMA IF NOT EXISTS items;

CREATE TABLE IF NOT EXISTS items.item (
    id VARCHAR(36)  PRIMARY KEY,
    name VARCHAR(120) NOT NULL
    );


INSERT INTO items.item(id, name) VALUES
    ('2cef2a1b-5ef9-4fc6-88fe-b2572f19ba1a', 'Item 01'),
    ('3d9d8092-b95d-4106-866f-96ef24902a2b', 'Item 02'),
    ('a2e43664-2adf-4abd-97ee-3782e98c3a3b', 'Item 03');