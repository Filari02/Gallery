package org.example.gallery.services.image;

import lombok.RequiredArgsConstructor;
import org.example.gallery.models.Image;
import org.example.gallery.models.Tag;
import org.example.gallery.repositories.image.ImageRepository;
import org.example.gallery.repositories.tag.TagRepository;
import org.example.gallery.views.image.ImagePostView;
import org.example.gallery.views.image.ImagePreviewView;
import org.example.gallery.views.image.ImageUpdateView;
import org.example.gallery.views.image.ImageView;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ImageServiceImp implements ImageService {

    private final ImageRepository imageRepository;

    private final TagRepository tagRepository;

    @Override
    public List<ImagePreviewView> getAllImages() {
        List<Image> images = imageRepository.findAll();

        List<ImagePreviewView> imagePreviewViews = new ArrayList<>();
        images.forEach(i -> {
            imagePreviewViews.add(ImagePreviewView.of(i));
        });
        return imagePreviewViews;
    }

    @Override
    public ImageView getImage(int id) {
        Image image = imageRepository.findById(id);
        return ImageView.of(image);
    }

    @Override
    public void postImage(ImagePostView imagePostView) {
        byte[] file;
        try {
            file = imagePostView.getFile().getBytes();

            Image image = new Image();
            image.setDescription(imagePostView.getDescription());
            image.setName(imagePostView.getName());

            image.setTags(getTagsForImage(image, imagePostView.getTags()));

            imageRepository.save(image, file);

        } catch (IOException e) {
            e.printStackTrace();
            //pridet loginima
        }
    }

    @Override
    public void updateImage(int id, ImageUpdateView imageUpdateView) {
        Image image = imageRepository.findById(id);
        image.setName(imageUpdateView.getName());
        image.setDescription(imageUpdateView.getDescription());
        image.setTags(getTagsForImage(image, imageUpdateView.getTags()));

        imageRepository.update(image);
    }

    @Override
    public void deleteImage(int id) {
        imageRepository.deleteById(id);
    }


    private Set<Tag> getTagsForImage(Image image, List<String> tagStrings) {

        Set<Tag> tags = new HashSet<>();

        for (String tagName : tagStrings) {
            Tag tag = tagRepository.findByName(tagName);

            if (tag == null) {
                Tag newTag = new Tag();
                newTag.setName(tagName);
                tags.add(newTag);
            }
            tags.add(tag);
        }
        return tags;
    }
}


