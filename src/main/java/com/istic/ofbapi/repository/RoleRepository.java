package com.istic.ofbapi.repository;


import com.istic.ofbapi.model.role.Role;
import com.istic.ofbapi.model.role.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName name);
}
