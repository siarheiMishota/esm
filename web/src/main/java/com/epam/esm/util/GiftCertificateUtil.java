package com.epam.esm.util;

import static com.epam.esm.dao.StringParameters.PATTERN_KEY_DESCRIPTION;
import static com.epam.esm.dao.StringParameters.PATTERN_KEY_NAME;
import static com.epam.esm.dao.StringParameters.PATTERN_KEY_SORT;
import static com.epam.esm.dao.StringParameters.PATTERN_KEY_TAG;

import com.epam.esm.entity.CodeOfEntity;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateParametersDto;
import com.epam.esm.entity.GiftCertificatePatchDto;
import com.epam.esm.exception.ResourceException;
import com.epam.esm.service.GiftCertificateService;
import java.util.Map;
import java.util.Optional;


public class GiftCertificateUtil {

    private final GiftCertificateService giftCertificateService;

    public GiftCertificateUtil(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    public void buildMapFromParameters(GiftCertificateParametersDto giftCertificateParametersDto,
                                       Map<String, String> parameterMap) {
        if (giftCertificateParametersDto.getName() != null) {
            parameterMap.put(PATTERN_KEY_NAME, giftCertificateParametersDto.getName());
        }
        if (giftCertificateParametersDto.getDescription() != null) {
            parameterMap.put(PATTERN_KEY_DESCRIPTION, giftCertificateParametersDto.getDescription());
        }
        if (giftCertificateParametersDto.getSort() != null) {
            String sortValue = replaceDateOnLastUpdateDateInLine(
                giftCertificateParametersDto.getSort());
            parameterMap.put(PATTERN_KEY_SORT, sortValue);
        }
        if (giftCertificateParametersDto.getTag() != null) {
            parameterMap.put(PATTERN_KEY_TAG, giftCertificateParametersDto.getTag());
        }
    }

    public GiftCertificate buildNotNullFieldInGiftCertificate(GiftCertificatePatchDto giftCertificatePatchDto) {
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateService.findById(
            giftCertificatePatchDto.getId());

        if (optionalGiftCertificate.isEmpty()) {
            throw new ResourceException("Gift certificate wasn't updated because id isn't found",
                CodeOfEntity.GIFT_CERTIFICATE);
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

    private String replaceDateOnLastUpdateDateInLine(String line) {
        return line.replaceAll("date", "lastUpdateDate");
    }
}
