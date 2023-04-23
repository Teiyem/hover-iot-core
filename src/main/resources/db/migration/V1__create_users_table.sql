CREATE TABLE tbl_user
(
    id       BIGSERIAL PRIMARY KEY,
    name     TEXT        NOT NULL,
    username TEXT UNIQUE NOT NULL,
    password TEXT        NOT NULL
);

CREATE TABLE user_tokens
(
    user_id SERIAL PRIMARY KEY,
    tokens  TEXT NOT NULL,
    CONSTRAINT user_tokens_fk FOREIGN KEY (user_id) REFERENCES tbl_user (id) ON DELETE CASCADE
);