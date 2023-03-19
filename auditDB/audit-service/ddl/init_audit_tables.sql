CREATE SCHEMA IF NOT EXISTS fitness;
CREATE TABLE fitness.audit
(
    uuid UUID NOT NULL,
    dt_create TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    user_uuid UUID NOT NULL,
    mail TEXT NOT NULL,
    fio TEXT NOT NULL,
    role TEXT NOT NULL,
    text TEXT NOT NULL,
    type TEXT NOT NULL,
    id TEXT NOT NULL,
    CONSTRAINT audit_pk PRIMARY KEY (uuid)
);
