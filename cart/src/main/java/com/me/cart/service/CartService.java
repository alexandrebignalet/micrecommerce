package com.me.cart.service;


import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.me.cart.domain.Cart;
import com.me.cart.domain.CartItem;
import com.me.cart.domain.Product;
import com.me.cart.security.SecurityUtils;
import com.me.cart.service.dto.CartItemDTO;
import com.me.cart.service.dto.ProductDTO;
import com.me.cart.service.mapper.CartItemMapper;
import org.springframework.messaging.simp.user.UserSessionRegistryAdapter;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.Objects;
import java.util.Optional;

@Service
public class CartService {

    public final static String CART = "ShoppingCart";
    private HazelcastInstance hazelcastInstance;
    private String sessionKeyName;
    private IMap<String, Cart> cartMap;
    private CartItemMapper cartItemMapper;


    public CartService(HazelcastInstance hazelcastInstance, CartItemMapper cartItemMapper) {
        this.hazelcastInstance = hazelcastInstance;
        this.cartItemMapper = cartItemMapper;
    }

    @PostConstruct
    public void init() {
        this.cartMap = hazelcastInstance.getMap(CART);
    }

    public CartItemDTO addItemToCart(CartItemDTO cartItemDTO) {
        Cart currentCart = getCurrentUserCart();
        CartItem cartItem = cartItemMapper.toEntity(cartItemDTO);
        currentCart.addCartItem(cartItem);
        cartMap.put(sessionKeyName, currentCart);
        return cartItemDTO;
    }

    public void removeItemFromCart(String id) {
        Cart currentCart = getCurrentUserCart();
        Optional<CartItem> removedItem = currentCart.getCartItems().stream().filter((item) -> Objects.equals(item.getProduct().getId(), id)).findFirst();
        removedItem.ifPresent(currentCart::removeCartItem);
        cartMap.put(sessionKeyName, currentCart);
    }

    public Cart getCurrentUserCart() {
        this.sessionKeyName = SecurityUtils.getCurrentUserLogin() + CART;

        Cart currentCart = cartMap.get(sessionKeyName);

        if(currentCart != null) return currentCart;

        Cart newCart = new Cart();
        cartMap.putIfAbsent(sessionKeyName, newCart);

        return newCart;
    }
}
