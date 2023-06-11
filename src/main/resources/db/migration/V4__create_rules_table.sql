CREATE TABLE TBL_RULE
(
    id          BIGSERIAL PRIMARY KEY,
    name        TEXT NOT NULL,
    description TEXT NOT NULL,
    updatedAt   DATE NOT NULL
);

CREATE TABLE TBL_RULE_TRIGGER
(
    id          BIGSERIAL PRIMARY KEY,
    name        TEXT NOT NULL,
    description TEXT NOT NULL,
    type        TEXT NOT NULL,
    rule_id     BIGINT,
    CONSTRAINT RULE_ID_FK FOREIGN KEY (rule_id) REFERENCES TBL_RULE (id)
);

CREATE TABLE TBL_RULE_TRIGGER_PARAM
(
    id          BIGSERIAL PRIMARY KEY,
    param_key   TEXT NOT NULL,
    param_value TEXT NOT NULL,
    trigger_id  BIGINT,
    CONSTRAINT TRIGGER_ID_FK FOREIGN KEY (trigger_id) REFERENCES TBL_RULE_TRIGGER (id)
);

CREATE TABLE TBL_RULE_ACTION
(
    id          BIGSERIAL PRIMARY KEY,
    name        TEXT NOT NULL,
    description TEXT NOT NULL,
    type        TEXT NOT NULL,
    rule_id     BIGINT,
    CONSTRAINT RULE_ID_FK FOREIGN KEY (rule_id) REFERENCES TBL_RULE (id)
);

CREATE TABLE TBL_RULE_ACTION_PARAM
(
    id          BIGSERIAL PRIMARY KEY,
    param_key   TEXT NOT NULL,
    param_value TEXT NOT NULL,
    action_id   BIGINT,
    CONSTRAINT RULE_ACTION_ID_FK FOREIGN KEY (action_id) REFERENCES TBL_RULE_ACTION (id)
);

CREATE TABLE TBL_SCENE
(
    id          BIGSERIAL PRIMARY KEY,
    name        TEXT NOT NULL,
    description TEXT NOT NULL,
    updatedAt   DATE NOT NULL
);

CREATE TABLE TBL_SCENE_ACTION
(
    id          BIGSERIAL PRIMARY KEY,
    name        TEXT NOT NULL,
    description TEXT NOT NULL,
    type        TEXT NOT NULL,
    scene_id    BIGINT,
    CONSTRAINT SCENE_ID_FK FOREIGN KEY (scene_id) REFERENCES TBL_SCENE (id)
);

CREATE TABLE TBL_SCENE_ACTION_PARAM
(
    id          BIGSERIAL PRIMARY KEY,
    param_key   TEXT NOT NULL,
    param_value TEXT NOT NULL,
    scene_id    BIGINT,
    CONSTRAINT SCENE_ACTION_ID_FK FOREIGN KEY (scene_id) REFERENCES TBL_SCENE_ACTION (id)
);