package com.me.cart.service.mapper;


import com.me.cart.domain.CartItem;
import com.me.cart.domain.Product;
import com.me.cart.service.dto.CartItemDTO;
import com.me.cart.service.dto.ProductDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Product and its DTO ProductDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductMapper.class,})
public interface CartItemMapper extends EntityMapper <CartItemDTO, CartItem> {
}
