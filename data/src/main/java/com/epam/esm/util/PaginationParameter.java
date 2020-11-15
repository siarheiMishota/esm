package com.epam.esm.util;

import static com.epam.esm.dao.StringParameters.PATTERN_LIMIT;
import static com.epam.esm.dao.StringParameters.PATTERN_OFFSET;
import static com.epam.esm.dao.impl.GiftCertificateDaoImpl.DEFAULT_LIMIT;
import static com.epam.esm.dao.impl.GiftCertificateDaoImpl.DEFAULT_OFFSET;

import java.util.Map;

public class PaginationParameter {

    public void fillInLimitAndOffset(Map<String, String> parameters, StringBuilder fullFindBuilder) {
        String valueOfLimit;
        String valueOfOffset;
        valueOfLimit = parameters.getOrDefault(PATTERN_LIMIT, DEFAULT_LIMIT);
        fullFindBuilder.append(" ").append(PATTERN_LIMIT)
            .append(" ")
            .append(valueOfLimit)
            .append(" ");

        valueOfOffset = parameters.getOrDefault(PATTERN_OFFSET, DEFAULT_OFFSET);
        fullFindBuilder.append(" ").append(PATTERN_OFFSET)
            .append(" ")
            .append(valueOfOffset)
            .append(" ");
    }
}
