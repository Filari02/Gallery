package org.example.gallery.controllers.tag;

import lombok.RequiredArgsConstructor;
import org.example.gallery.services.tag.TagService;
import org.example.gallery.views.tag.TagView;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TagController {

    private final TagService tagService;

    @GetMapping()
    @ResponseStatus(value = HttpStatus.OK)
    public List<TagView> getAllTags() {
        return tagService.getAllTags();
    }

    @GetMapping("/names")
    @ResponseStatus(value = HttpStatus.OK)
    public List<String> getAllTagNames() {
        return tagService.getAllTagNames();
    }


}
