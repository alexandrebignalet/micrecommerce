package com.me.cart.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class Cart implements Serializable {
    private Set<CartItem> cartItems = new HashSet<>();

    public BigDecimal getTotal() {
        return cartItems.stream().map(CartItem::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addCartItems(CartItem cartItem) {
        this.cartItems.add(cartItem);
    }

    public void removeCartItems(CartItem cartItem) {
        this.cartItems.remove(cartItem);
    }

    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
