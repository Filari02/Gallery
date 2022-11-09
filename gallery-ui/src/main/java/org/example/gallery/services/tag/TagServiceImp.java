package org.example.gallery.services.tag;

import lombok.RequiredArgsConstructor;
import org.example.gallery.models.Tag;
import org.example.gallery.repositories.tag.TagRepository;
import org.example.gallery.views.tag.TagView;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TagServiceImp implements TagService {

    private final TagRepository tagRepository;

    @Override
    public List<TagView> getAllTags() {
        List<Tag> tags = tagRepository.findAll();
        return tags.stream().map(TagView::of).collect(Collectors.toList());
    }

    @Override
    public List<String> getAllTagNames() {
        List<Tag> tags = tagRepository.findAll();
        return tags.stream().map(Tag::getName).collect(Collectors.toList());
    }
}
