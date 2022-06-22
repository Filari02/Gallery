create table TAGS (
    ID serial,
    NAME varchar(16) unique not null,
    primary key (ID)
);