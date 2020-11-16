package com.epam.esm.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.epam.esm.entity.CodeOfEntity;
import com.epam.esm.entity.PaginationDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.TagDto;
import com.epam.esm.exception.ResourceException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.TagService;
import com.epam.esm.util.PaginationUtil;
import com.epam.esm.util.converter.TagConverter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
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
    private final TagConverter tagConverter;
    private final PaginationUtil paginationUtil;

    public TagController(TagService tagService, TagConverter tagConverter, PaginationUtil paginationUtil) {
        this.tagService = tagService;
        this.tagConverter = tagConverter;
        this.paginationUtil = paginationUtil;
    }

    @GetMapping
    public CollectionModel<TagDto> getTags(@Valid PaginationDto paginationDto) {
        Map<String, String> parameterMap = new HashMap<>();
        paginationUtil.fillInMapFromPaginationDto(paginationDto, parameterMap);

        List<Tag> tags = tagService.findAll(parameterMap);

        if (tags.isEmpty()) {
            throw new ResourceNotFoundException("Requested resource not found ", CodeOfEntity.TAG);
        }

        List<TagDto> tagDtos = tagConverter.convertListToListDto(tags);
        tagDtos.forEach(
            tagDto -> tagDto.add(linkTo(methodOn(TagController.class).getTagById(tagDto.getId())).withSelfRel()));

        Link link = linkTo(TagController.class).withSelfRel();
        return CollectionModel.of(tagDtos, link);
    }

    @GetMapping("/{id}")
    public EntityModel<TagDto> getTagById(@PathVariable @Min(1) @NumberFormat long id) {
        Optional<Tag> optionalTag = tagService.findById(id);
        if (optionalTag.isEmpty()) {
            throw new ResourceException(String.format("Requested resource not found (id=%d)", id), CodeOfEntity.TAG);
        }
        TagDto tagDto = tagConverter.convertToDto(optionalTag.get());
        tagDto.add(linkTo(methodOn(TagController.class).getTagById(tagDto.getId())).withSelfRel());
        return EntityModel.of(tagDto);
    }

    @PostMapping
    public EntityModel<TagDto> createTag(@RequestBody @Valid TagDto tagDto) {
        Tag tag = tagConverter.convertFromDto(tagDto);
        if (!tagService.add(tag)) {
            throw new ResourceException("Tag wasn't added because one is exist", CodeOfEntity.TAG);
        }

        TagDto result = tagConverter.convertToDto(tag);
        result.add(linkTo(methodOn(TagController.class).getTagById(tagDto.getId())).withSelfRel());
        return EntityModel.of(result);

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
