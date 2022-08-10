package org.example.gallery.views.image;

import lombok.Builder;
import lombok.Data;
import org.example.gallery.models.Image;
import org.example.gallery.models.Tag;

import java.util.Set;

@Data
@Builder
public class ImagePreviewView {

    private int id;

    private String name;

    private Set<Tag> tags;

    private String thumbnailPath;

    public static ImagePreviewView of (Image image) {
        return ImagePreviewView.builder()
                .id(image.getId())
                .name(image.getName())
                .tags(image.getTags())
                .thumbnailPath(image.getThumbnailFilePath())
                .build();
    }
}
