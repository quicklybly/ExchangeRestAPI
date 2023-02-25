package com.quicklybly.exchangerestapi.repositories;

import com.quicklybly.exchangerestapi.entities.Role;
import com.quicklybly.exchangerestapi.entities.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleEnum name);
}
