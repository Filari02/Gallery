package org.example.gallery.views.image;

import lombok.Builder;
import lombok.Data;
import org.example.gallery.models.Image;
import org.example.gallery.models.Tag;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@Builder
public class ImagePreviewView {
    private int id;
    private String name;
    private List<String> tags;
    private byte[] thumbnail;
    private String thumbnailType;

    public static ImagePreviewView of (Image image, Function<String, byte[]> thumbnailFunction, Function<String, String> imageTypeFunction) {
        return ImagePreviewView.builder()
                .id(image.getId())
                .name(image.getName())
                .tags(image.getTags().stream().map(Tag::getName).collect(Collectors.toList()))
                .thumbnail(thumbnailFunction.apply(image.getThumbnailFilePath()))
                .thumbnailType(imageTypeFunction.apply(image.getThumbnailFilePath()))
                .build();
    }
}
