package com.epam.esm.controller;

import com.epam.esm.entity.CodeOfEntity;
import com.epam.esm.entity.PaginationDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.TagDto;
import com.epam.esm.exception.ResourceException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.TagService;
import com.epam.esm.util.PaginationUtil;
import com.epam.esm.util.adapter.TagAdapter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import org.springframework.format.annotation.NumberFormat;
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
    private final TagAdapter tagAdapter;
    private final PaginationUtil paginationUtil;

    public TagController(TagService tagService, TagAdapter tagAdapter, PaginationUtil paginationUtil) {
        this.tagService = tagService;
        this.tagAdapter = tagAdapter;
        this.paginationUtil = paginationUtil;
    }

    @GetMapping
    public List<TagDto> getTags(@Valid PaginationDto paginationDto) {
        Map<String, String> parameterMap = new HashMap<>();
        paginationUtil.fillInMapFromPaginationDto(paginationDto, parameterMap);

        List<Tag> tags;
        if (parameterMap.isEmpty()) {
            tags = tagService.findAll();
        } else {
            tags = tagService.findAll(parameterMap);
            if (tags.isEmpty()) {
                throw new ResourceNotFoundException("Requested resource not found ", CodeOfEntity.USER);
            }
        }

        return tagAdapter.adaptListToListDto(tags);
    }

    @GetMapping("/{id}")
    public TagDto getTagById(@PathVariable @Min(1) @NumberFormat long id) {
        Optional<Tag> optionalTag = tagService.findById(id);
        if (optionalTag.isEmpty()) {
            throw new ResourceException(String.format("Requested resource not found (id=%d)", id), CodeOfEntity.TAG);
        }
        return tagAdapter.adaptToDto(optionalTag.get());
    }

    @PostMapping
    public TagDto createTag(@RequestBody @Valid TagDto tagDto) {
        Tag tag = tagAdapter.adaptDtoTo(tagDto);
        if (!tagService.add(tag)) {
            throw new ResourceException("Tag wasn't added because one is exist", CodeOfEntity.TAG);
        }
        return tagAdapter.adaptToDto(tag);

    }

    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable long id) {
        if (id < 0) {
            throw new ResourceException("Tag wasn't deleted because id is negative", CodeOfEntity.TAG);
        }

        if (tagService.findById(id).isEmpty()) {
            throw new ResourceException(String.format("Id= %d is not exist", id), CodeOfEntity.TAG);
        }
        tagService.delete(id);
    }
}
