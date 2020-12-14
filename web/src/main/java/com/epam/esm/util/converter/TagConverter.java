package com.epam.esm.util.converter;

import com.epam.esm.entity.Tag;
import com.epam.esm.entity.TagDto;
import java.util.List;
import java.util.stream.Collectors;

public class TagConverter {

    private TagConverter() {
    }

    public static TagDto convertToDto(Tag tag) {
        TagDto tagDto = new TagDto();
        tagDto.setId(tag.getId());
        tagDto.setName(tag.getName());
        return tagDto;
    }

    public static Tag convertFromDto(TagDto tagDto) {
        Tag tag = new Tag();
        tag.setName(tagDto.getName());
        return tag;
    }

    public static List<TagDto> convertListToListDto(List<Tag> tags) {
        return tags.stream()
            .map(TagConverter::convertToDto)
            .collect(Collectors.toList());
    }
}
