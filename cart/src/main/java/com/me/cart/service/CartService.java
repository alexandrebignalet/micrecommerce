package com.me.cart.service;


import com.me.cart.domain.Cart;
import com.me.cart.domain.Product;
import com.me.cart.service.dto.ProductDTO;
import com.netflix.discovery.DiscoveryClient;

public class CartService {

    private DiscoveryClient discoveryClient;

    public CartService(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    public void addProductToCart(ProductDTO productDTO) {
    }

    private Cart getCurrentUserCart() {
        return new Cart();
    }
}
