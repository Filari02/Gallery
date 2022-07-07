create table TAGS (
    ID serial primary key,
    NAME varchar(16) unique not null
);