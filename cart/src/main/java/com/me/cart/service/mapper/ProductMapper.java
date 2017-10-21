package com.me.cart.service.mapper;


import com.me.cart.domain.Product;
import com.me.cart.service.dto.ProductDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity Product and its DTO ProductDTO.
 */
@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper <ProductDTO, Product> {
}
