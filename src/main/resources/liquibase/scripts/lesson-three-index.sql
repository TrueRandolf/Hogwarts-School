-- liquibase formatted sql

-- changeset TrueRandolf:1
create index index_name on student (name);

-- changeset TrueRandolf:2
create index index_namecolor on faculty (name,color);
