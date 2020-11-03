package com.epam.esm.entity;

import java.math.BigDecimal;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class GiftCertificateMostChangeDto {

    @Size(max = 500)
    private String description;

    @Min(0)
    private BigDecimal price;

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
}
