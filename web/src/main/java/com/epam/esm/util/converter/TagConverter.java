package com.epam.esm.util.converter;

import com.epam.esm.entity.Tag;
import com.epam.esm.entity.TagDto;
import java.util.List;
import java.util.stream.Collectors;

public class TagConverter {

    public TagDto convertToDto(Tag tag) {
        TagDto tagDto = new TagDto();
        tagDto.setId(tag.getId());
        tagDto.setName(tag.getName());
        return tagDto;
    }

    public Tag convertFromDto(TagDto tagDto) {
        Tag tag = new Tag();
        tag.setName(tagDto.getName()/*.replaceAll("\\s+","")*/);
        return tag;
    }

    public List<TagDto> convertListToListDto(List<Tag> tags) {
        return tags.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
}
