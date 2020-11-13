package com.epam.esm.entity;

import java.math.BigDecimal;
import javax.validation.constraints.Min;

public class OrderPatchDto {

    private long id;
    @Min(0)
    private BigDecimal cost;

    @Min(0)
    private Long idGiftCertificate;

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Long getIdGiftCertificate() {
        return idGiftCertificate;
    }

    public void setIdGiftCertificate(Long idGiftCertificate) {
        this.idGiftCertificate = idGiftCertificate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
