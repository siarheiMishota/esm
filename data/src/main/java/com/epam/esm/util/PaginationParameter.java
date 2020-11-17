package com.epam.esm.util;

import static com.epam.esm.dao.StringParameters.PATTERN_LIMIT;
import static com.epam.esm.dao.StringParameters.PATTERN_OFFSET;

import com.epam.esm.entity.Pagination;

public class PaginationParameter {

    public void buildLimitAndOffset(Pagination pagination, StringBuilder fullFindBuilder) {
        fullFindBuilder.append(" ")
            .append(PATTERN_LIMIT).append(" ")
            .append(pagination.getLimit()).append(" ")
            .append(PATTERN_OFFSET).append(" ")
            .append(pagination.getOffset()).append(" ");
    }
}
