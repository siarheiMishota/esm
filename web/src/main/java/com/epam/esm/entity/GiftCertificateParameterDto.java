package com.epam.esm.entity;

import java.util.List;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class GiftCertificateParameterDto {

    @Size(max = 50)
    private String name;

    @Size(max = 500)
    private String description;

    private List<String> tag;

    @Pattern(regexp = "((date|name)(:asc|:desc|),)*((date|name)(:asc|:desc|))")
    private String sort;

    public List<String> getTag() {
        return tag;
    }

    public void setTag(List<String> tag) {
        this.tag = tag;
    }

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
