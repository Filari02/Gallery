package org.example.gallery.views.image;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ImagePostView {
    private MultipartFile file;
    private String name;
    private String description;
    private List<String> tags;
}
