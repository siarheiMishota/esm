package com.epam.esm.entity;

public class Pagination {

    public static final int DEFAULT_LIMIT = 50;
    public static final int DEFAULT_OFFSET = 0;

    private int limit;
    private long offset;

    public Pagination(int limit, long offset) {
        this.limit = limit;
        this.offset = offset;
    }

    public Pagination() {
        this.limit = DEFAULT_LIMIT;
        this.offset = DEFAULT_OFFSET;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Pagination that = (Pagination) o;

        if (limit != that.limit) {
            return false;
        }
        return offset == that.offset;
    }

    @Override
    public int hashCode() {
        int result = limit;
        result = 31 * result + (int) (offset ^ (offset >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder()
            .append("Pagination( limit= ")
            .append(limit)
            .append(", offset= ")
            .append(offset)
            .append(");")
            .toString();
    }
}
