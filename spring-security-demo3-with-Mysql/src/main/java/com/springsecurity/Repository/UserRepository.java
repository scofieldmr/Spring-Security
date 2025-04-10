package com.springsecurity.Repository;

import com.springsecurity.entity.MyUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<MyUsers, Long> {

    Optional<MyUsers> findByUsername(String username);
}
