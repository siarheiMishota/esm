package com.epam.esm.entity;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class GiftCertificateParametersDto {

    @Size(max = 50)
    private String name;

    @Size(max = 500)
    private String description;

    @Pattern(regexp = "(data|name):?(asc|desc|)")
    private String sort;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
