package com.me.catalog.service.impl;

import com.me.catalog.service.TagService;
import com.me.catalog.domain.Tag;
import com.me.catalog.repository.TagRepository;
import com.me.catalog.service.dto.TagDTO;
import com.me.catalog.service.mapper.TagMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing Tag.
 */
@Service
public class TagServiceImpl implements TagService{

    private final Logger log = LoggerFactory.getLogger(TagServiceImpl.class);

    private final TagRepository tagRepository;

    private final TagMapper tagMapper;

    public TagServiceImpl(TagRepository tagRepository, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    /**
     * Save a tag.
     *
     * @param tagDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TagDTO save(TagDTO tagDTO) {
        log.debug("Request to save Tag : {}", tagDTO);
        Tag tag = tagMapper.toEntity(tagDTO);
        tag = tagRepository.save(tag);
        return tagMapper.toDto(tag);
    }

    /**
     *  Get all the tags.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<TagDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Tags");
        return tagRepository.findAll(pageable)
            .map(tagMapper::toDto);
    }

    /**
     *  Get one tag by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public TagDTO findOne(String id) {
        log.debug("Request to get Tag : {}", id);
        Tag tag = tagRepository.findOne(id);
        return tagMapper.toDto(tag);
    }

    /**
     *  Delete the  tag by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Tag : {}", id);
        tagRepository.delete(id);
    }
}
