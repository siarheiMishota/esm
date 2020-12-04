package com.epam.esm.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.epam.esm.entity.CodeOfEntity;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.PaginationDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.TagDto;
import com.epam.esm.exception.ResourceException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.TagService;
import com.epam.esm.util.converter.PaginationConverter;
import com.epam.esm.util.converter.TagConverter;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final PaginationConverter paginationConverter;

    public TagController(TagService tagService,
                         TagConverter tagConverter,
                         PaginationConverter paginationConverter) {
        this.tagService = tagService;
        this.tagConverter = tagConverter;
        this.paginationConverter = paginationConverter;
    }

    @GetMapping
    public CollectionModel<TagDto> getTags(@Valid PaginationDto paginationDto) {
        Pagination pagination = paginationConverter.convertFromDto(paginationDto);
        List<Tag> tags = tagService.findAll(pagination);

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
    public EntityModel<TagDto> getTagById(@PathVariable long id) {
        if (id < 0) {
            throw new ResourceNotFoundException("Tag not found", CodeOfEntity.TAG);
        }

        Optional<Tag> optionalTag = tagService.findById(id);
        if (optionalTag.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Requested resource not found (id=%d)", id),
                CodeOfEntity.TAG);
        }
        TagDto tagDto = tagConverter.convertToDto(optionalTag.get());
        tagDto.add(linkTo(methodOn(TagController.class).getTagById(tagDto.getId())).withSelfRel());
        return EntityModel.of(tagDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/mostUsedByUserHighestCost")
    public EntityModel<TagDto> getTagMostUsedByUserHighestCost() {
        Optional<Tag> optionalTag = tagService.findMostUsedByUserHighestCost();
        if (optionalTag.isEmpty()) {
            throw new ResourceNotFoundException("Requested resource not found", CodeOfEntity.TAG);
        }

        TagDto tagDto = tagConverter.convertToDto(optionalTag.get());
        tagDto.add(linkTo(methodOn(TagController.class).getTagById(tagDto.getId())).withSelfRel());
        return EntityModel.of(tagDto);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
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

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable long id) {
        if (id < 0) {
            throw new ResourceException("Tag wasn't deleted because id is negative", CodeOfEntity.TAG);
        }

        if (tagService.findById(id).isEmpty()) {
            throw new ResourceNotFoundException(String.format("Id= %d is not exist", id), CodeOfEntity.TAG);
        }
        tagService.delete(id);
    }
}
