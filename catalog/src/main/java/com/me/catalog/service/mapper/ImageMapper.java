package com.me.catalog.service.mapper;

import com.me.catalog.domain.*;
import com.me.catalog.service.dto.ImageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Image and its DTO ImageDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ImageMapper extends EntityMapper <ImageDTO, Image> {
    
    

}
