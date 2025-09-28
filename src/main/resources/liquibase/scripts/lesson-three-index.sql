-- liquibase formatted sql

 -- changeset TrueRandolf:0
 create table if not exists student(
 id long primary key,
 age int,
 name varchar(255),
 faculty_id long
 );

 create table if not exists faculty(
  id long primary key,
  color varchar(255),
  name varchar(255)
 );

-- changeset TrueRandolf:1
 create index index_name on student (name);

-- changeset TrueRandolf:2
 create index index_namecolor on faculty (name,color);
