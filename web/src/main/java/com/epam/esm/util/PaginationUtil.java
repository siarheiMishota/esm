package com.epam.esm.util;

import static com.epam.esm.dao.StringParameters.PATTERN_LIMIT;
import static com.epam.esm.dao.StringParameters.PATTERN_OFFSET;

import com.epam.esm.entity.PaginationDto;
import java.util.Map;

public class PaginationUtil {
    public void fillInMapFromPaginationDto(PaginationDto paginationDto, Map<String,String> parameterMap){
        if (paginationDto.getLimit() != 0) {
            parameterMap.put(PATTERN_LIMIT, "" + paginationDto.getLimit());
        }
        if (paginationDto.getOffset() != 0) {
            parameterMap.put(PATTERN_OFFSET, "" + paginationDto.getOffset());
        }
    }
}
