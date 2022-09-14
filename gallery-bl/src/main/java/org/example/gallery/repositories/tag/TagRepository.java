package org.example.gallery.repositories.tag;

import org.example.gallery.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    Set<Tag> findByNameIn(List<String> names);
}