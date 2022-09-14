package org.example.gallery.services.image;

import org.example.gallery.views.image.ImagePostView;
import org.example.gallery.views.image.ImagePreviewView;
import org.example.gallery.views.image.ImageUpdateView;
import org.example.gallery.views.image.ImageView;

import java.util.List;

public interface ImageService {
    List<ImagePreviewView> getAllImages();

    ImageView getImage(int id);

    void postImage(ImagePostView imagePostView);

    void updateImage(int id, ImageUpdateView imageUpdateView);

    void deleteImage(int id);
}
