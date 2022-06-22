create table IMAGES (
    ID serial,
    NAME varchar(64),
    UPLOAD_DATE date,
    DESCRIPTION varchar(512),
    FILE_PATH varchar(256),
    THUMBNAIL_FILE_PATH varchar(256),
    USER_ID int,
    primary key (ID),
    foreign key (USER_ID)
        references USERS(ID)
);