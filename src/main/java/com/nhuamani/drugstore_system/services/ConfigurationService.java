package com.nhuamani.drugstore_system.services;

import com.nhuamani.drugstore_system.dtos.ConfigurationFormDTO;
import org.springframework.web.multipart.MultipartFile;


public interface ConfigurationService {

    ConfigurationFormDTO getConfiguration();

    void saveConfiguration(ConfigurationFormDTO dto, MultipartFile logoFile);

}