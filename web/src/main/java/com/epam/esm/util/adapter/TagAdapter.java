package com.epam.esm.util.adapter;

import com.epam.esm.entity.Tag;
import com.epam.esm.entity.TagDto;
import java.util.List;
import java.util.stream.Collectors;

public class TagAdapter {

    public TagDto adaptToDto(Tag tag){
        TagDto tagDto=new TagDto();
        tagDto.setId(tag.getId());
        tagDto.setName(tag.getName());
        return tagDto;
    }

    public Tag adaptDtoTo(TagDto tagDto){
        Tag tag=new Tag();
        tag.setName(tagDto.getName());
        return tag;
    }

    public List<TagDto> adaptListToListDto(List<Tag> tags){
        return tags.stream()
            .map(this::adaptToDto)
            .collect(Collectors.toList());
    }
}
