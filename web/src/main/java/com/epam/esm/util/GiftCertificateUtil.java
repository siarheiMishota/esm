package com.epam.esm.util;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificatePatchDto;


public class GiftCertificateUtil {

    public GiftCertificate buildNotNullFieldInGiftCertificate(GiftCertificatePatchDto giftCertificatePatchDto) {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(giftCertificatePatchDto.getId());

        if (giftCertificatePatchDto.getName() != null) {
            giftCertificate.setName(giftCertificatePatchDto.getName());
        }

        if (giftCertificatePatchDto.getDescription() != null) {
            giftCertificate.setDescription(giftCertificatePatchDto.getDescription());
        }

        if (giftCertificatePatchDto.getPrice() != null) {
            giftCertificate.setPrice(giftCertificatePatchDto.getPrice());
        }

        if (giftCertificatePatchDto.getDuration() > 0) {
            giftCertificate.setDuration(giftCertificatePatchDto.getDuration());
        }

        if (giftCertificatePatchDto.getTags() != null && !giftCertificatePatchDto
            .getTags().isEmpty()) {
            giftCertificate.setTags(giftCertificatePatchDto.getTags());
        }
        return giftCertificate;
    }
}
