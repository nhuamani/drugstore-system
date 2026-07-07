package com.nhuamani.drugstore_system.repositories;

import com.nhuamani.drugstore_system.models.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Integer> {

    Optional<Configuration> findBySettingKey(String name);

}
