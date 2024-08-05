CREATE TABLE IF NOT EXISTS items.flaggedusers (
    questionOwner VARCHAR(36) NOT NULL,
    answerOwner VARCHAR(36) NOT NULL,
    level VARCHAR(15) NOT NULL,
    reason TEXT NOT NULL,
    dateCreated timestamp default now()
);
CREATE INDEX idx_flaggedusers_idquestion ON items.flaggedusers(questionOwner);
CREATE INDEX idx_flaggedusers_idanswer ON items.flaggedusers(answerOwner);
CREATE INDEX idx_flaggedusers_ids ON items.flaggedusers(questionOwner, answerOwner);
CREATE INDEX idx_flaggedusers_rason ON items.flaggedusers(reason);