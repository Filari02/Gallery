package org.example.gallery.services.image;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageServiceImp implements ImageService {
    private final ImageRepository imageRepository;
    private final TagRepository tagRepository;
    private final static Logger logger = LogManager.getLogger(ImageServiceImp.class);

    @Override
    public List<ImagePreviewView> getAllImages() {
        List<Image> images = imageRepository.findAll();

        List<ImagePreviewView> imagePreviewViews = new ArrayList<>();
        images.stream().map(ImagePreviewView::of).collect(Collectors.toList());
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
            image.setTags(getTagsForImage(imagePostView.getTags()));

            imageRepository.save(image, file, imagePostView.getFile().getOriginalFilename());

        } catch (IOException e) {
            logger.error(e, e);
        }
    }

    @Override
    public void updateImage(int id, ImageUpdateView imageUpdateView) {
        Image image = imageRepository.findById(id);
        image.setName(imageUpdateView.getName());
        image.setDescription(imageUpdateView.getDescription());
        image.setTags(getTagsForImage(imageUpdateView.getTags()));

        imageRepository.update(image);
    }

    @Override
    public void deleteImage(int id) {
        imageRepository.deleteById(id);
    }

    private Set<Tag> getTagsForImage(List<String> tagStrings) {
        Set<Tag> tags = tagRepository.findByNameIn(tagStrings);

        Map<String, Tag> tagMap = tags.stream().collect(Collectors.toMap(Tag::getName, Function.identity()));
        Set<Tag> allTags = tagStrings.stream().map(t -> Optional.ofNullable(tagMap.get(t))
            .orElseGet(() -> new Tag(t)))
            .collect(Collectors.toSet());

        return allTags;
    }
}


