package org.example.gallery.services.tag;

import org.example.gallery.views.tag.TagView;

import java.util.List;

public interface TagService {
    List<TagView> getAllTags();
    List<String> getAllTagNames();
}
