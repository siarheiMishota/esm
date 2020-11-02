package com.epam.esm.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class GiftCertificateDto {

    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String creationDate;
    private String lastUpdateDate;
    private int duration;
    private List<Tag> tags;

    public GiftCertificateDto() {
    }

    public GiftCertificateDto(long id,
                              String name,
                              String description,
                              BigDecimal price,
                              LocalDateTime creationDate,
                              LocalDateTime lastUpdateDate,
                              int duration,
                              List<Tag> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.creationDate = creationDate.toString();
        this.lastUpdateDate = lastUpdateDate.toString();
        this.duration = duration;
        this.tags = tags;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
         this.tags = tags;
    }
}
