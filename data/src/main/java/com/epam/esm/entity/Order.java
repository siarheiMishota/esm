package com.epam.esm.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Order {

    private long id;
    private BigDecimal cost;
    private LocalDateTime date;
    private GiftCertificate giftCertificate;

    public Order() {
    }

    public Order(BigDecimal cost, LocalDateTime date, GiftCertificate giftCertificate) {
        this.cost = cost;
        this.date = date;
        this.giftCertificate = giftCertificate;
    }

    public Order(long id, BigDecimal cost, LocalDateTime date, GiftCertificate giftCertificate) {
        this.id = id;
        this.cost = cost;
        this.date = date;
        this.giftCertificate = giftCertificate;
    }

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

    public GiftCertificate getGiftCertificate() {
        return giftCertificate;
    }

    public void setGiftCertificate(GiftCertificate giftCertificate) {
        this.giftCertificate = giftCertificate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Order order = (Order) o;

        if (id != order.id) {
            return false;
        }
        if (cost != null ? !cost.equals(order.cost) : order.cost != null) {
            return false;
        }
        if (date != null ? !date.equals(order.date) : order.date != null) {
            return false;
        }
        return giftCertificate != null ? giftCertificate.equals(order.giftCertificate) : order.giftCertificate == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (giftCertificate != null ? giftCertificate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder()
            .append("Order( id= ")
            .append(id).append(", cost= ")
            .append(cost).append(", timeStamp= ")
            .append(date).append(", giftCertificate= ")
            .append(giftCertificate).append("); ")
            .toString();
    }
}
