package org.com.example.finance.repository;

import org.com.example.finance.entity.Role;
import org.com.example.finance.entity.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(UserRole name);
}