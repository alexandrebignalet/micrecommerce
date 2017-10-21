package com.me.catalog.service.mapper;

import com.me.catalog.domain.*;
import com.me.catalog.service.dto.CategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Category and its DTO CategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategoryMapper extends EntityMapper <CategoryDTO, Category> {
    
    

}
