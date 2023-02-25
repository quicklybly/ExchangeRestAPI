package com.quicklybly.exchangerestapi.repositories;

import com.quicklybly.exchangerestapi.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
    Boolean existsByPassword(String password);
    Optional<UserEntity> findByUsername(String username);
}
