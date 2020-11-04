package com.epam.esm.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TagDto {

    private long id;

    @NotNull
    @Size(max = 50)
    private String name;

    public TagDto() {
    }

    public TagDto(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
