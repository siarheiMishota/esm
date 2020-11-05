package com.epam.esm.util;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificatePatchDto;
import com.epam.esm.exception.ResourceException;
import com.epam.esm.service.GiftCertificateService;
import java.util.Optional;
import org.springframework.http.HttpStatus;


public class GiftCertificateUtil {

    private final GiftCertificateService giftCertificateService;

    public GiftCertificateUtil(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    public GiftCertificate fillNotNullFieldInGiftCertificate(GiftCertificatePatchDto giftCertificatePatchDto) {
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateService.findById(
            giftCertificatePatchDto.getId());

        if (optionalGiftCertificate.isEmpty()) {
            throw new ResourceException(HttpStatus.BAD_REQUEST,
                "Gift certificate wasn't updated because id isn't found");
        }

        GiftCertificate giftCertificate = optionalGiftCertificate.get();

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
