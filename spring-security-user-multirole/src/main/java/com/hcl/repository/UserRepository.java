package com.hcl.repository;

import com.hcl.entity.MyUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<MyUsers, Long> {

    MyUsers findByUsername(String username);

    boolean existsByUsername(String username);
}
