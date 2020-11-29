package com.epam.esm.entity;

import java.util.ArrayList;
import java.util.List;

public class GiftCertificateParameter {

    private String name;
    private String description;
    private List<String> tags=new ArrayList<>();
    private String sort;

    public GiftCertificateParameter() {
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
