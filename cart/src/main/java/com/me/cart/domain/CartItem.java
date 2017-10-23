package com.me.cart.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class CartItem implements Serializable {
    private int quantity;
    private Product product;

    public CartItem(){}

    public CartItem(int quantity, Product product) {
        this.quantity = quantity;
        this.product = product;
    }

    public BigDecimal getTotal() {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public CartItem quantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public CartItem product(Product product) {
        this.product = product;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CartItem cartItem = (CartItem) o;
        if (cartItem.getProduct() == null || getProduct() == null) {
            return false;
        }
        return Objects.equals(cartItem.getProduct(), getProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getProduct().getId() + getQuantity());
    }

    @Override
    public String toString() {
        return "CartItem: {"+
            "quantity: " + this.getQuantity() + "," +
            "product: " + this.getProduct() + "}";
    }
}
