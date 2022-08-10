package org.example.gallery.views.image;

import lombok.Data;
import org.example.gallery.models.Tag;

import java.util.List;
import java.util.Set;

@Data
public class ImageUpdateView {

    private String name;

    private String description;

    private List<String> tags;
}
