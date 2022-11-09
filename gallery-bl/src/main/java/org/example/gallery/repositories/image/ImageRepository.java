package org.example.gallery.repositories.image;

import org.example.gallery.models.Image;
import org.example.gallery.models.Tag;

import java.io.IOException;
import java.util.List;

public interface ImageRepository {
    Image findById(int id);

    List<Image> findByAttributes(String searchTerm, List<String> tagNames);

    List<Image> findAll();

    void save(Image image, byte[] bytes, String fileName) throws IOException;

    void update(Image image);

    boolean deleteById(int id);
}
