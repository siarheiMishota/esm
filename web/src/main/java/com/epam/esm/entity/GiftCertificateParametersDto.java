package com.epam.esm.entity;

import java.util.List;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class GiftCertificateParametersDto {

    @Size(max = 50)
    private List<String> name;

    @Size(max = 500)
    private List<String> description;

    private String tag;

    @Pattern(regexp = "((date|name)(:asc|:desc|),)*((date|name)(:asc|:desc|))")
    private String sort;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
