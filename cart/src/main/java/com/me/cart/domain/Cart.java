package com.me.cart.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Cart implements Serializable {
    private Set<CartItem> cartItems = new HashSet<>();

    public BigDecimal getTotal() {
        return cartItems.stream().map(CartItem::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addCartItem(CartItem cartItem) {
        boolean added = this.cartItems.add(cartItem);
        if(!added) {
            Optional<CartItem> maybeCartItem = this.cartItems.stream().filter((item) -> item.equals(cartItem)).findFirst();
            maybeCartItem.ifPresent(item -> item.quantity(cartItem.getQuantity() + item.getQuantity()));
        }
    }

    public int size() {
        return cartItems.size();
    }

    public void removeCartItem(CartItem cartItem) {
        this.cartItems.remove(cartItem);
    }

    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    @Override
    public String toString() {
        return "Cart: {" +
            "total: "+this.getTotal()+"," +
            "items: "+this.getCartItems() + "}";
    }
}
