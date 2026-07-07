package com.nhuamani.drugstore_system.services.impl;

import com.nhuamani.drugstore_system.dtos.ConfigurationDTO;
import com.nhuamani.drugstore_system.mappers.ConfigurationMapper;
import com.nhuamani.drugstore_system.models.Configuration;
import com.nhuamani.drugstore_system.repositories.ConfigurationRepository;
import com.nhuamani.drugstore_system.services.ConfigurationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigurationServiceImpl implements ConfigurationService{

    private final ConfigurationRepository repository;

    public ConfigurationServiceImpl(ConfigurationRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ConfigurationDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(ConfigurationMapper::toDTO)
                .toList();
    }

    @Override
    public ConfigurationDTO findById(Integer id) {
        return repository.findById(id)
                .map(ConfigurationMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Configuración no encontrada"));
    }


    @Override
    public void save(ConfigurationDTO dto) {

        Configuration entity;

        if (dto.getId() == null) {
            entity = new Configuration();
        } else {
            entity = repository.findById(dto.getId())
                    .orElseThrow(() ->
                            new RuntimeException("Configuración no encontrada"));
        }

        ConfigurationMapper.updateEntity(entity, dto);

        repository.save(entity);
    }

    @Override
    public void delete(Integer id) {

        if (!repository.existsById(id)) {
            throw new RuntimeException("Configuración no encontrada");
        }

        repository.deleteById(id);
    }

}