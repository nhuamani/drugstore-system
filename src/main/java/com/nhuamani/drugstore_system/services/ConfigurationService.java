package com.nhuamani.drugstore_system.services;

import com.nhuamani.drugstore_system.dtos.ConfigurationFormDTO;


public interface ConfigurationService {

    ConfigurationFormDTO getConfiguration();

    void saveConfiguration(ConfigurationFormDTO dto);

}