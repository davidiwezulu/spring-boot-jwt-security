package com.davidiwezulu.project.repository;

import com.davidiwezulu.project.model.Role;
import com.davidiwezulu.project.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}

