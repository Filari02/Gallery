package org.example.gallery.views.image;

import lombok.Builder;
import lombok.Data;
import org.example.gallery.models.Image;
import org.example.gallery.models.Tag;
import org.example.gallery.models.User;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@Builder
public class ImageView {
    private int id;
    private String name;
    private Date uploadDate;
    private String description;
    private byte[] file;
    private String fileType;
    private String username;
    private List<String> tags;

    public static ImageView of(Image image, Function<String, byte[]> imageFunction, Function<String, String> imageTypeFunction) {
        return ImageView.builder()
                .id(image.getId())
                .name(image.getName())
                .uploadDate(image.getUploadDate())
                .description(image.getDescription())
                .file(imageFunction.apply(image.getFilePath()))
                .fileType(imageTypeFunction.apply(image.getFilePath()))
                .username(image.getUser().getName())
                .tags(image.getTags().stream().map(Tag::getName).collect(Collectors.toList()))
                .build();
    }

}
