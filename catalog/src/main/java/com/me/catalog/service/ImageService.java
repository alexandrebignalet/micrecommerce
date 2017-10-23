package com.me.catalog.service;

import com.me.catalog.service.dto.ImageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Image.
 */
public interface ImageService {

    /**
     * Save a image.
     *
     * @param imageDTO the entity to save
     * @return the persisted entity
     */
    ImageDTO save(ImageDTO imageDTO);

    /**
     *  Get all the images.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ImageDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" image.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ImageDTO findOne(String id);

    /**
     *  Delete the "id" image.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
