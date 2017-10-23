package com.me.catalog.service.mapper;

import com.me.catalog.domain.*;
import com.me.catalog.service.dto.TagDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Tag and its DTO TagDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TagMapper extends EntityMapper <TagDTO, Tag> {
    
    

}
