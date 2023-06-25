CREATE TABLE TBL_VAULT
(
    id   BIGSERIAL PRIMARY KEY,
    key  TEXT UNIQUE NOT NULL,
    data TEXT        NOT NULL
);

CREATE TABLE TBL_ROOM
(
    id   BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE TBL_DEVICE_GROUP
(
    id   BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE TBL_DEVICE
(
    id         BIGSERIAL PRIMARY KEY,
    name       TEXT        NOT NULL,
    host       TEXT        NOT NULL,
    firmware   TEXT        NOT NULL,
    status     TEXT        NOT NULL,
    room_id    BIGINT REFERENCES TBL_ROOM (id),
    group_id   BIGINT REFERENCES TBL_DEVICE_GROUP (id),
    type       TEXT        NOT NULL,
    platform   TEXT        NOT NULL,
    uuid       TEXT UNIQUE NOT NULL,
    updated_at DATE        NOT NULL
);


CREATE TABLE TBL_ATTRIBUTE
(
    id         BIGSERIAL PRIMARY KEY,
    name       TEXT NOT NULL,
    value      TEXT NOT NULL,
    type       TEXT NOT NULL,
    updated_at DATE NOT NULL,
    device_id  BIGINT REFERENCES TBL_DEVICE (id)
);

CREATE TABLE TBL_METADATA
(
    id        BIGSERIAL PRIMARY KEY,
    key       TEXT NOT NULL,
    value     TEXT NOT NULL,
    device_id BIGINT REFERENCES TBL_DEVICE (id)
);

CREATE TABLE TBL_FIRMWARE
(
    id       BIGSERIAL PRIMARY KEY,
    filename TEXT  NOT NULL,
    version  TEXT  NOT NULL,
    type     TEXT  NOT NULL,
    platform TEXT  NOT NULL,
    file     BYTEA NOT NULL
);

INSERT INTO TBL_ROOM (name)
VALUES ('Living Room'),
       ('Bedroom'),
       ('Bathroom'),
       ('Kitchen');