package com.nhuamani.drugstore_system.services;

import com.nhuamani.drugstore_system.dtos.ConfigurationDTO;

import java.util.List;

public interface ConfigurationService {

    List<ConfigurationDTO> findAll();

    ConfigurationDTO findById(Integer id);

    void save(ConfigurationDTO dto);

    void delete(Integer id);

}