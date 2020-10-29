package com.epam.esm.controller;

import com.epam.esm.entity.Tag;
import com.epam.esm.entity.TagDto;
import com.epam.esm.exception.NotFoundIdException;
import com.epam.esm.exception.SortParametersException;
import com.epam.esm.service.TagService;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tags")
@Validated
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<TagDto> getTags(@RequestParam(required = false) String sort) {

        if (tagService.tagValidate(sort)) {
            throw new SortParametersException(HttpStatus.NOT_ACCEPTABLE, "sort", sort);
        }

        Map<String, String> parameters = new HashMap<>();
        parameters.put("sorts", sort);

        return tagService.findAll(parameters)
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
