create table TAGS_IMAGES (
    IMAGE_ID int not null,
    TAG_ID int not null,
    primary key (IMAGE_ID, TAG_ID),
    foreign key (IMAGE_ID)
        references IMAGES(ID),
    foreign key (TAG_ID)
        references TAGS(ID)
);