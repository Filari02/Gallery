create index IDX_USER_IMAGES
    on IMAGE(USER_ID);
create index IDX_TAG_IMAGES
    on IMAGE_TAGS(TAG_ID);
create index IDX_IMAGE_TAGS
    on IMAGE_TAGS(IMAGE_ID);