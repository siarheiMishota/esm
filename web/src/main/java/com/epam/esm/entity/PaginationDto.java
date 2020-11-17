package com.epam.esm.entity;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class PaginationDto {

    @Min(0)
    @Max(100)
    private int limit;

    @Min(0)
    private long offset;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }
}
