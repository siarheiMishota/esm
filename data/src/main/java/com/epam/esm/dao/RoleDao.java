package com.epam.esm.dao;

import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.Role;
import java.util.List;
import java.util.Optional;

public interface RoleDao {

    List<Role> findAll(Pagination pagination);

    Optional<Role> findById(long id);

    Optional<Role> findByName(String name);

    Role add(Role role);

    void delete(Role role);
}
