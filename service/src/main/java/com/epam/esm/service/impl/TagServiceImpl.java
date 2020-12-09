package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.CodeOfEntity;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.TagService;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;

    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public List<Tag> findAll(Pagination pagination) {
        return tagDao.findAll(pagination);
    }

    @Override
    public Optional<Tag> findById(long id) {
        return tagDao.findById(id);
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return tagDao.findByName(name);
    }

    @Override
    public Optional<Tag> findMostUsedByUserHighestCost() {
        return tagDao.findMostUsedByUserHighestCost();
    }

    @Override
    public boolean add(Tag tag) {
        if (tag == null) {
            return false;
        }

        Optional<Tag> optionalTag = findByName(tag.getName());
        if (optionalTag.isPresent()) {
            tag.setId(optionalTag.get().getId());
        } else {
            tagDao.add(tag);
        }
        return true;
    }

    @Override
    public void delete(long id) {
        Optional<Tag> optionalTag = findById(id);
        if (optionalTag.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Resource is not found, (id=%d)", id), CodeOfEntity.TAG);
        }

        tagDao.delete(optionalTag.get());
    }
}
