alter table USERS rename to "user";

alter table "user"
    alter column USER_TYPE TYPE varchar(32);

alter table "user"
    add constraint UNIQUE_EMAIL unique (EMAIL);

drop type USER_TYPE;

alter table TAGS rename to TAG;

alter table IMAGES rename to IMAGE;

alter table TAGS_IMAGES rename to IMAGE_TAGS;

