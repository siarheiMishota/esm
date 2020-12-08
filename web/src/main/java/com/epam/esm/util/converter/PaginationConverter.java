package com.epam.esm.util.converter;

import static com.epam.esm.entity.Pagination.DEFAULT_LIMIT;
import static com.epam.esm.entity.Pagination.DEFAULT_OFFSET;

import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.PaginationDto;

public class PaginationConverter {

    private PaginationConverter() {
    }

    public static Pagination convertFromDto(PaginationDto paginationDto) {
        Pagination pagination = new Pagination();
        int limit;
        int offset;
        if (paginationDto.getLimit() <= 0) {
            limit = DEFAULT_LIMIT;
        } else {
            limit = paginationDto.getLimit();
        }

        if (paginationDto.getOffset() <= 0) {
            offset = DEFAULT_OFFSET;
        } else {
            offset = paginationDto.getOffset();
        }

        pagination.setLimit(limit);
        pagination.setOffset(offset);
        return pagination;
    }
}
