package com.springsecurity.repository;

import com.springsecurity.dto.RoleDto;
import com.springsecurity.entity.ERole;
import com.springsecurity.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(ERole role);
}
