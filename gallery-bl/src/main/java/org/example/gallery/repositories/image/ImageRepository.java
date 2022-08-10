package org.example.gallery.repositories.image;

import org.example.gallery.models.Image;
import org.springframework.stereotype.Repository;

import java.awt.image.BufferedImage;
import java.util.List;

public interface ImageRepository {

    Image findById(int id);

    List<Image> findAll();

    void save(Image image, byte[] bytes);

    void update(Image image);

    void deleteById(int id);
}
