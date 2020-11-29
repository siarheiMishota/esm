package com.epam.esm.util.converter;

import com.epam.esm.entity.GiftCertificateParameter;
import com.epam.esm.entity.GiftCertificateParameterDto;
import java.util.List;

public class GiftCertificateParameterConverter {

    public GiftCertificateParameter convertFromDto(GiftCertificateParameterDto giftCertificateParameterDto) {
        GiftCertificateParameter giftCertificateParameter = new GiftCertificateParameter();
        giftCertificateParameter.setName(giftCertificateParameterDto.getName());
        giftCertificateParameter.setDescription(giftCertificateParameterDto.getDescription());
        giftCertificateParameter.setSort(giftCertificateParameterDto.getSort());

        if (giftCertificateParameterDto.getTag() == null) {
            giftCertificateParameter.setTags(List.of());
        } else {
            giftCertificateParameter.setTags(giftCertificateParameterDto.getTag());
        }
        return giftCertificateParameter;
    }
}
