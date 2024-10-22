package com.hcl.repository;

import com.hcl.entity.ERole;
import com.hcl.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Role findByName(ERole name);
}
