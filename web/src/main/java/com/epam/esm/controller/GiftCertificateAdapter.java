package com.epam.esm.controller;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateDto;
import com.epam.esm.entity.Tag;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GiftCertificateAdapter {
    public GiftCertificate adaptDtoTo(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName(giftCertificateDto.getName());
        giftCertificate.setDescription(giftCertificateDto.getDescription());
        giftCertificate.setPrice(giftCertificateDto.getPrice());
        giftCertificate.setCreationDate(LocalDateTime.parse(giftCertificateDto.getCreationDate()));
        giftCertificate.setLastUpdateDate(LocalDateTime.parse(giftCertificateDto.getLastUpdateDate()));
        giftCertificate.setDuration(giftCertificateDto.getDuration());

        List<Tag> tagsDto = giftCertificateDto.getTags();
        if (tagsDto == null) {
            giftCertificate.setTags(new ArrayList<>());
        } else {
            giftCertificate.setTags(tagsDto.stream()
                .map(tagDto -> new Tag(tagDto.getId(), tagDto.getName()))
                .collect(Collectors.toList()));
        }
        return giftCertificate;
    }

    public GiftCertificateDto adaptToDto(GiftCertificate giftCertificate) {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setId(giftCertificate.getId());
        giftCertificateDto.setName(giftCertificate.getName());
        giftCertificateDto.setDescription(giftCertificate.getDescription());
        giftCertificateDto.setPrice(giftCertificate.getPrice());
        giftCertificateDto.setCreationDate(giftCertificate.getCreationDate().toString());
        giftCertificateDto.setLastUpdateDate(giftCertificate.getLastUpdateDate().toString());
        giftCertificateDto.setDuration(giftCertificate.getDuration());
        giftCertificateDto.setTags(giftCertificate.getTags());
        return giftCertificateDto;
    }

    public List<GiftCertificateDto> adaptListToListDto(List<GiftCertificate> giftCertificates) {
        return giftCertificates.stream()
            .map(this::adaptToDto)
            .collect(Collectors.toList());
    }
}
