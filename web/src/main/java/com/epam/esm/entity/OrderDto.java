package com.epam.esm.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import org.springframework.hateoas.RepresentationModel;

public class OrderDto extends RepresentationModel<OrderDto> {

    private long id;

    @Null
    private BigDecimal cost;

    private LocalDateTime date;

    @NotNull
    @Min(0)
    private Long idGiftCertificate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getIdGiftCertificate() {
        return idGiftCertificate;
    }

    public void setIdGiftCertificate(Long idGiftCertificate) {
        this.idGiftCertificate = idGiftCertificate;
    }
}
