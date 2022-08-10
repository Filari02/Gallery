package org.example.gallery.views.image;

import lombok.Builder;
import lombok.Data;
import org.example.gallery.models.Tag;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@Data
public class ImagePostView {

    private MultipartFile file;

    private String name;

    private String description;

    private List<String> tags;



}
