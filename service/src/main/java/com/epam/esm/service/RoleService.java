package com.epam.esm.service;

import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.Role;
import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> findAll(Pagination pagination);

    Optional<Role> findById(long id);

    Optional<Role> findByName(String name);

    boolean add(Role role);

    void delete(long id);
}
