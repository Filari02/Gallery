package org.example.gallery.views.image;

import lombok.Data;

import java.util.List;

@Data
public class SearchTerm {
    private String name;
    private List<String> tagNames;
}
