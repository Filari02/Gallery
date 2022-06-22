create type USER_TYPE as enum (
    'ADMIN',
    'SIMPLE_USER'
    );


create table USERS (
    ID SERIAL,
    EMAIL varchar(128),
    NAME varchar(16),
    PASSWORD varchar(256),
    USER_TYPE USER_TYPE,
    primary key (ID)
);

