package com.me.cart.web.rest;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import com.codahale.metrics.annotation.Timed;
import com.me.cart.domain.Cart;
import com.me.cart.domain.CartItem;
import com.me.cart.domain.Product;
import com.me.cart.service.CartService;
import com.me.cart.service.dto.CartItemDTO;
import com.me.cart.service.mapper.ProductMapper;
import com.me.cart.web.rest.util.HeaderUtil;
import com.me.cart.web.rest.vm.LoggerVM;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

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
        cartService.addItemToCart(cartItemDTO);
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping("/cart/items/{id}")
    @Timed
    public ResponseEntity removeCartItem(@PathVariable String id) {
        cartService.removeItemFromCart(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();

    }
}
