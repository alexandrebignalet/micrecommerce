package com.me.catalog.service.impl;

import com.me.catalog.service.ImageService;
import com.me.catalog.domain.Image;
import com.me.catalog.repository.ImageRepository;
import com.me.catalog.service.dto.ImageDTO;
import com.me.catalog.service.mapper.ImageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing Image.
 */
@Service
public class ImageServiceImpl implements ImageService{

    private final Logger log = LoggerFactory.getLogger(ImageServiceImpl.class);

    private final ImageRepository imageRepository;

    private final ImageMapper imageMapper;

    public ImageServiceImpl(ImageRepository imageRepository, ImageMapper imageMapper) {
        this.imageRepository = imageRepository;
        this.imageMapper = imageMapper;
    }

    /**
     * Save a image.
     *
     * @param imageDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ImageDTO save(ImageDTO imageDTO) {
        log.debug("Request to save Image : {}", imageDTO);
        Image image = imageMapper.toEntity(imageDTO);
        image = imageRepository.save(image);
        return imageMapper.toDto(image);
    }

    /**
     *  Get all the images.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<ImageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Images");
        return imageRepository.findAll(pageable)
            .map(imageMapper::toDto);
    }

    /**
     *  Get one image by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public ImageDTO findOne(String id) {
        log.debug("Request to get Image : {}", id);
        Image image = imageRepository.findOne(id);
        return imageMapper.toDto(image);
    }

    /**
     *  Delete the  image by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Image : {}", id);
        imageRepository.delete(id);
    }
}
