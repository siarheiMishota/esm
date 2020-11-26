package com.epam.esm.util.converter;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateDto;
import com.epam.esm.entity.TagDto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GiftCertificateConverter {

    private final TagConverter tagConverter;

    public GiftCertificateConverter(TagConverter tagConverter) {
        this.tagConverter = tagConverter;
    }

    public GiftCertificate convertFromDto(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(giftCertificateDto.getId());
        giftCertificate.setName(giftCertificateDto.getName());
        giftCertificate.setDescription(giftCertificateDto.getDescription());
        giftCertificate.setPrice(giftCertificateDto.getPrice());
        giftCertificate.setDuration(giftCertificateDto.getDuration());

        List<TagDto> tagsDto = giftCertificateDto.getTags();
        if (tagsDto == null) {
            giftCertificate.setTags(new ArrayList<>());
        } else {
            giftCertificate.setTags(tagsDto.stream()
                .map(tagConverter::convertFromDto)
                .collect(Collectors.toList()));
        }
        return giftCertificate;
    }

    public GiftCertificateDto convertToDto(GiftCertificate giftCertificate) {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setId(giftCertificate.getId());
        giftCertificateDto.setName(giftCertificate.getName());
        giftCertificateDto.setDescription(giftCertificate.getDescription());
        giftCertificateDto.setPrice(giftCertificate.getPrice());
        giftCertificateDto.setCreationDate(giftCertificate.getCreationDate().toString());
        giftCertificateDto.setLastUpdateDate(giftCertificate.getLastUpdateDate().toString());
        giftCertificateDto.setDuration(giftCertificate.getDuration());
        giftCertificateDto.setTags(tagConverter.convertListToListDto(giftCertificate.getTags()));
        return giftCertificateDto;
    }

    public List<GiftCertificateDto> convertListToListDto(List<GiftCertificate> giftCertificates) {
        return giftCertificates.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
}
