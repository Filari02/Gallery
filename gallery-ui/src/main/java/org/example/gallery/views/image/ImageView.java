package org.example.gallery.views.image;

import lombok.Builder;
import lombok.Data;
import org.example.gallery.models.Image;
import org.example.gallery.models.Tag;

import java.util.Date;
import java.util.Set;

@Data
@Builder
public class ImageView {
    private int id;
    private String name;
    private Date uploadDate;
    private String description;
    private String filePath;
    private String userName;
    private Set<Tag> tags;

    public static ImageView of(Image image) {
        return ImageView.builder()
                .id(image.getId())
                .name(image.getName())
                .uploadDate(image.getUploadDate())
                .description(image.getDescription())
                .filePath(image.getFilePath())
                .userName(image.getUser().getName())
                .tags(image.getTags())
                .build();
    }

}
