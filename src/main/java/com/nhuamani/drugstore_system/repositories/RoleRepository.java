package com.nhuamani.drugstore_system.repositories;

import com.nhuamani.drugstore_system.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

    Role findByDescription(String description);

}
