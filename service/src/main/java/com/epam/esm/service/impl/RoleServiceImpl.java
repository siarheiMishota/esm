package com.epam.esm.service.impl;

import com.epam.esm.dao.RoleDao;
import com.epam.esm.entity.CodeOfEntity;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.Role;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.RoleService;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public List<Role> findAll(Pagination pagination) {
        return roleDao.findAll(pagination);
    }

    @Override
    public Optional<Role> findById(long id) {
        return roleDao.findById(id);
    }

    @Override
    public Optional<Role> findByName(String name) {
        return roleDao.findByName(name);
    }

    @Override
    public boolean add(Role role) {
        if (role == null) {
            return false;
        }
        Optional<Role> optionalTag = findByName(role.getName());
        if (optionalTag.isPresent()) {
            role.setId(optionalTag.get().getId());
        } else {
            roleDao.add(role);
        }
        return true;
    }

    @Override
    public void delete(long id) {
        Optional<Role> optionalRole = findById(id);
        if (optionalRole.isEmpty()) {
            throw new ResourceNotFoundException("Resource is not found", CodeOfEntity.ROLE);
        }

        roleDao.delete(optionalRole.get());
    }
}
