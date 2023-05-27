CREATE TABLE tbl_vault
(
    id   BIGSERIAL PRIMARY KEY,
    key  TEXT UNIQUE NOT NULL,
    data TEXT        NOT NULL
);

CREATE TABLE tbl_room
(
    id   BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE tbl_device
(
    id       BIGSERIAL PRIMARY KEY,
    name     TEXT        NOT NULL,
    host     TEXT,
    firmware TEXT        NOT NULL,
    status   TEXT        NOT NULL,
    room_id  BIGINT REFERENCES tbl_room (id),
    type     TEXT        NOT NULL,
    platform TEXT        NOT NULL,
    uuid     TEXT UNIQUE NOT NULL
);

CREATE TABLE tbl_attribute
(
    id        BIGSERIAL PRIMARY KEY,
    name      TEXT NOT NULL,
    value     TEXT NOT NULL,
    type      TEXT NOT NULL,
    device_id BIGINT REFERENCES tbl_device (id)
);

INSERT INTO tbl_room (name)
VALUES ('Living Room'),
       ('Bedroom'),
       ('Bathroom'),
       ('Kitchen');