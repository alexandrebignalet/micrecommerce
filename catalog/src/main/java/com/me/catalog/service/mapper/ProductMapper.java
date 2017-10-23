package com.me.catalog.service.mapper;

import com.me.catalog.domain.*;
import com.me.catalog.service.dto.ProductDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Product and its DTO ProductDTO.
 */
@Mapper(componentModel = "spring", uses = {CategoryMapper.class, TagMapper.class, ImageMapper.class, })
public interface ProductMapper extends EntityMapper <ProductDTO, Product> {
}
