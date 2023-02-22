package com.zabivonikl.vaadindemo.data.entity;

import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;

import javax.persistence.Entity;

@Entity
public class InventoryItem extends AbstractEntity {
    private String title;
    private String vendor;
    private int piecesLeft;
    private double price;
    private String category;

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

    public String getFormatedPrice() {
        return Double.toString(price) + '₽';
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPiecesLeft() {
        return piecesLeft;
    }

    public void setPiecesLeft(int piecesLeft) {
        this.piecesLeft = piecesLeft;
    }
}
