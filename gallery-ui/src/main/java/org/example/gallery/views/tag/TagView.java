package org.example.gallery.views.tag;

import lombok.Builder;
import lombok.Data;
import org.example.gallery.models.Tag;

@Data
@Builder
public class TagView {
    private int id;
    private String name;

    public static TagView of (Tag tag) {
        return TagView.builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
    }
}
