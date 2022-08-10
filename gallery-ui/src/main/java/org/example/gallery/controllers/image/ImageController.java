package org.example.gallery.controllers.image;

import lombok.RequiredArgsConstructor;
import org.example.gallery.services.image.ImageService;
import org.example.gallery.views.image.ImagePostView;
import org.example.gallery.views.image.ImagePreviewView;
import org.example.gallery.views.image.ImageUpdateView;
import org.example.gallery.views.image.ImageView;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    @GetMapping()
    @ResponseStatus(value = HttpStatus.OK)
    public List<ImagePreviewView> getAllImages() {
        return imageService.getAllImages();
    }

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public ImageView getImage(@PathVariable int id) {
        return imageService.getImage(id);
    }

    @PostMapping(consumes = {"multipart/form-data"})
    @ResponseStatus(value = HttpStatus.CREATED)
    public void uploadImage(@RequestParam("image") MultipartFile imageFile, @ModelAttribute ImagePostView imagePostView) {
        imagePostView.setFile(imageFile);
        imageService.postImage(imagePostView);
    }

    @PutMapping("{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void editImage(@PathVariable int id, @RequestBody ImageUpdateView imageUpdateView) {
        imageService.updateImage(id, imageUpdateView);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteImage(@PathVariable int id) {
        imageService.deleteImage(id);
    }
}
