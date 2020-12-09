package com.epam.esm.util.converter;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateDto;
import com.epam.esm.entity.TagDto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GiftCertificateConverter {

    private GiftCertificateConverter() {
    }

    public static GiftCertificate convertFromDto(GiftCertificateDto giftCertificateDto) {
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
                .map(TagConverter::convertFromDto)
                .collect(Collectors.toList()));
        }
        return giftCertificate;
    }

    public static GiftCertificateDto convertToDto(GiftCertificate giftCertificate) {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setId(giftCertificate.getId());
        giftCertificateDto.setName(giftCertificate.getName());
        giftCertificateDto.setDescription(giftCertificate.getDescription());
        giftCertificateDto.setPrice(giftCertificate.getPrice());
        giftCertificateDto.setCreationDate(giftCertificate.getCreationDate().toString());
        giftCertificateDto.setLastUpdateDate(giftCertificate.getLastUpdateDate().toString());
        giftCertificateDto.setDuration(giftCertificate.getDuration());
        giftCertificateDto.setTags(TagConverter.convertListToListDto(giftCertificate.getTags()));
        return giftCertificateDto;
    }

    public static List<GiftCertificateDto> convertListToListDto(List<GiftCertificate> giftCertificates) {
        return giftCertificates.stream()
            .map(GiftCertificateConverter::convertToDto)
            .collect(Collectors.toList());
    }
}
