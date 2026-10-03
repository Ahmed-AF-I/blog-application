package dev.ahmed.blog.services.impl;

import dev.ahmed.blog.domain.entities.Tag;
import dev.ahmed.blog.repositories.TagRepository;
import dev.ahmed.blog.services.TagService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public List<Tag> getTags() {
        return tagRepository.findAllWithPostCount();
    }

    @Transactional
    @Override
    public List<Tag> createTags(Set<String> tagNames) {

        List<Tag> existingTags = tagRepository.findByNameIn(tagNames);

        Set<String> existingTagNames = existingTags.stream()
                .map(Tag::getName)
                .collect(Collectors.toSet());

        var newTags = tagNames.stream()
                .filter(name -> !existingTagNames.contains(name))
                .map(name -> Tag.builder()
                        .name(name)
                        .posts(new HashSet<>())
                        .build())
                .toList();

        List<Tag> savedTags = new ArrayList<>();

        if(!newTags.isEmpty()) {
            savedTags = tagRepository.saveAll(newTags);
        }

        savedTags.addAll(existingTags);
        return savedTags;
    }

    @Transactional
    @Override
    public void deleteTag(UUID id) {
        tagRepository.findById(id).ifPresent(tag ->{
            if(!tag.getPosts().isEmpty()) {
                throw new IllegalStateException("Can't delete tag which has posts");
            }
            tagRepository.delete(tag);
        });
    }

    @Override
    public Tag getTagById(UUID id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tag with id " + id + " not found"));
    }

    @Override
    public List<Tag> getTagByIds(Set<UUID> tagIds) {
        List<Tag> foundTags = tagRepository.findAllById(tagIds);
        if(foundTags.size() != tagIds.size()) {
            throw new EntityNotFoundException("Not all specified tags found");
        }
        return foundTags;
    }
}
