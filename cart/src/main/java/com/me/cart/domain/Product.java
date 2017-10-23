package com.me.cart.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;

public class Product implements Serializable {

    private String id;

    private String name;

    private BigDecimal price;

    private byte[] image;

    private String fileContentType;

    public Product id(String id) {
        this.id = id;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Product price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public byte[] getImage() {
        return image;
    }

    public Product image(byte[] file) {
        this.image = file;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public Product fileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
        return this;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        if (product.getId() == null || getId() == null || product.getName() == null || getName() == null
            || product.getFileContentType() == null || getFileContentType() == null ||
            product.getImage() == null || getImage() == null || product.getPrice() == null || getPrice() == null) {
            return false;
        }
        return Objects.equals(getId(), product.getId()) && Objects.equals(getName(), product.getName())
            && Objects.equals(getPrice(), product.getPrice()) && Arrays.equals(getImage(), product.getImage())
            && Objects.equals(getFileContentType(), product.getFileContentType());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId()+getPrice()+getName()+getFileContentType());
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", price='" + getPrice() + "'" +
            "}";
    }
}
