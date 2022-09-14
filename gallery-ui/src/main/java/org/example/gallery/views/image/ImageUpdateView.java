package org.example.gallery.views.image;

import lombok.Data;

import java.util.List;

@Data
public class ImageUpdateView {
    private String name;
    private String description;
    private List<String> tags;
}
