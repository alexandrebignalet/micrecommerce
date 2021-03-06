package com.me.catalog.service;

import com.me.catalog.service.dto.TagDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Tag.
 */
public interface TagService {

    /**
     * Save a tag.
     *
     * @param tagDTO the entity to save
     * @return the persisted entity
     */
    TagDTO save(TagDTO tagDTO);

    /**
     *  Get all the tags.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TagDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" tag.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TagDTO findOne(String id);

    /**
     *  Delete the "id" tag.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
