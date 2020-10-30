package com.epam.esm.controller;

import com.epam.esm.entity.Tag;
import com.epam.esm.entity.TagDto;
import com.epam.esm.exception.NotFoundIdException;
import com.epam.esm.service.TagService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.constraints.Min;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tags")
@Validated
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

    @GetMapping("/giftCertificates")
    public List<TagDto> getTagsa() {
        return tagService.findAll()
            .stream()
            .map(tag -> new TagDto(tag.getId(), tag.getName()))
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TagDto getTagById(@PathVariable @Min(1) @NumberFormat long id) {

        Optional<Tag> optionalTag = tagService.findById(id);
        if (optionalTag.isEmpty()) {
            throw new NotFoundIdException("id", id);
        }

        Tag tag = optionalTag.get();
        return new TagDto(tag.getId(), tag.getName());
    }

}
