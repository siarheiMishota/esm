package com.epam.esm.entity;

import java.text.DateFormat;

public class GiftCertificate {
    private long id;
    private String description;
    private double price;
    private DateFormat createDate;
    private DateFormat lastUpdateDate;
    private int duration;

    public GiftCertificate() {
    }

    public GiftCertificate(long id, String description, double price, DateFormat createDate, DateFormat lastUpdateDate, int duration) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.duration = duration;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public DateFormat getCreateDate() {
        return createDate;
    }

    public void setCreateDate(DateFormat createDate) {
        this.createDate = createDate;
    }

    public DateFormat getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(DateFormat lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GiftCertificate that = (GiftCertificate) o;

        if (id != that.id) return false;
        if (Double.compare(that.price, price) != 0) return false;
        if (duration != that.duration) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;
        return lastUpdateDate != null ? lastUpdateDate.equals(that.lastUpdateDate) : that.lastUpdateDate == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + (description != null ? description.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (lastUpdateDate != null ? lastUpdateDate.hashCode() : 0);
        result = 31 * result + duration;
        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("GiftCertificate( id= ")
                .append(id)
                .append(", description= ")
                .append(description)
                .append(", price= ")
                .append(price)
                .append(", create date= ")
                .append(createDate)
                .append(", last update date= ")
                .append(lastUpdateDate)
                .append(", duration= ")
                .append(duration)
                .append(");")
                .toString();
    }
}
