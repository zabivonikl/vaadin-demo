package com.zabivonikl.vaadindemo.data.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "items")
public class InventoryItem extends AbstractEntity {
    @NotEmpty
    private String title;
    private String vendor;
    private String category;
    private int piecesLeft;
    private double price;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getFormatedPrice() {
        return Double.toString(price) + '₽';
    }

    public int getPiecesLeft() {
        return piecesLeft;
    }

    public void setPiecesLeft(int piecesLeft) {
        this.piecesLeft = piecesLeft;
    }
}
