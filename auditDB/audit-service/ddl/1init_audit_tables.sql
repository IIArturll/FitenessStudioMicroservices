CREATE SCHEMA IF NOT EXISTS fitness;
CREATE TABLE fitness.user
(
    uuid UUID NOT NULL,
    dt_create TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    user_uuid UUID NIT NULL,
    mail TEXT NOT NULL UNIQUE,
    fio TEXT NOT NULL,
    role TEXT NOT NULL,
    text TEXT NOT NULL,
    type TEXT NOT NULL,
    id TEXT NOT NULL,
    CONSTRAINT uuid_user PRIMARY KEY (id)
);
