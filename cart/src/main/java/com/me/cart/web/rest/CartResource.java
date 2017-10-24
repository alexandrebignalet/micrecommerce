package com.me.cart.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.me.cart.domain.Cart;
import com.me.cart.security.SecurityUtils;
import com.me.cart.service.CartService;
import com.me.cart.service.dto.CartItemDTO;
import com.me.cart.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller for view and managing Log Level at runtime.
 */
@RestController
@RequestMapping("/api")
public class CartResource {

    private static final String ENTITY_NAME = "cartItem";
    private CartService cartService;

    public CartResource(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cart")
    @Timed
    public Cart getCart() {
        return cartService.getCurrentUserCart();
    }

    @PostMapping("/cart")
    @Timed
    public ResponseEntity addCartItem(@RequestBody @Valid CartItemDTO cartItemDTO) {
        String userLogin = SecurityUtils.getCurrentUserLogin();
        cartService.addItemToCart(cartItemDTO);
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping("/cart/items/{id}")
    @Timed
    public ResponseEntity removeCartItem(@PathVariable String id) {
        String userLogin = SecurityUtils.getCurrentUserLogin();
        cartService.removeItemFromCart(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();

    }
}
