package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao {
    List<Tag> findAll();

    Optional<Tag> findById(int id);

    Optional<Tag> findByName(String name);

    Tag add(Tag tag);

    void update(Tag tag);

    void delete(int id);

}
