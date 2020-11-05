package com.epam.esm.controller;

import com.epam.esm.entity.Tag;
import com.epam.esm.entity.TagDto;
import com.epam.esm.exception.ResourceException;
import com.epam.esm.service.TagService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<TagDto> getTags() {
        return tagService.findAll()
            .stream()
            .map(tag -> new TagDto(tag.getId(), tag.getName()))
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TagDto getTagById(@PathVariable @Min(1) @NumberFormat long id) {
        Optional<Tag> optionalTag = tagService.findById(id);
        if (optionalTag.isEmpty()) {
            throw new ResourceException(HttpStatus.BAD_REQUEST,
                String.format("Requested resource not found (id=%d)", id));
        }
        return new TagDto(optionalTag.get().getId(), optionalTag.get().getName());
    }

    @PostMapping
    public TagDto createTag(@RequestBody @Valid TagDto tagDto) {
        Tag tag = new Tag(tagDto.getName());
        if (tagService.add(tag)) {
            return new TagDto(tag.getId(), tag.getName());
        } else {
            throw new ResourceException(HttpStatus.BAD_REQUEST, "Tag wasn't added because one is exist");
        }
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteTag(@PathVariable long id) {
        if (id < 0) {
            throw new ResourceException(HttpStatus.BAD_REQUEST, "Tag wasn't deleted because id is negative");
        }

        if (tagService.findById(id).isEmpty()) {
            throw new ResourceException(HttpStatus.BAD_REQUEST, String.format("Id= %d is not exist", id));
        }
        tagService.delete(id);
        return HttpStatus.OK;
    }
}
