package org.example.gallery.controllers.image;

import lombok.RequiredArgsConstructor;
import org.example.gallery.services.image.ImageService;
import org.example.gallery.views.image.ImagePostView;
import org.example.gallery.views.image.ImagePreviewView;
import org.example.gallery.views.image.ImageUpdateView;
import org.example.gallery.views.image.ImageView;
import org.example.gallery.views.image.SearchTerm;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
@CrossOrigin(origins = "*", allowedHeaders = "*")
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

    @GetMapping("/search")
    @ResponseStatus(value = HttpStatus.OK)
    public List<ImagePreviewView> getImageByAttributes(@RequestParam(name = "name", required = false) String searchTerm, @RequestParam(required = false) List<String> tagNames) {
        SearchTerm searchTerm1 = new SearchTerm();
        searchTerm1.setName(searchTerm);
        searchTerm1.setTagNames(tagNames);
        return imageService.getImagesByAttributes(searchTerm1);
    }

    @PostMapping(consumes = {"multipart/form-data"})
    @ResponseStatus(value = HttpStatus.CREATED)
    public void uploadImage(@ModelAttribute ImagePostView imagePostView) {
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
