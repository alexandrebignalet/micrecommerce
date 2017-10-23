package com.me.catalog.repository;

import com.me.catalog.domain.Tag;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Tag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TagRepository extends MongoRepository<Tag, String> {

}
