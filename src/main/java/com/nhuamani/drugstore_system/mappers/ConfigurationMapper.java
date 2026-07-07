package com.nhuamani.drugstore_system.mappers;

import com.nhuamani.drugstore_system.dtos.ConfigurationDTO;
import com.nhuamani.drugstore_system.models.Configuration;

public class ConfigurationMapper {

    public static ConfigurationDTO toDTO(Configuration entity) {

        ConfigurationDTO dto = new ConfigurationDTO();

        dto.setId(entity.getId());
        dto.setSettingKey(entity.getSettingKey());
        dto.setSettingValue(entity.getSettingValue());
        dto.setDescription(entity.getDescription());

        return dto;
    }

    public static Configuration toEntity(ConfigurationDTO dto) {

        Configuration entity = new Configuration();

        entity.setId(dto.getId());
        entity.setSettingKey(dto.getSettingKey());
        entity.setSettingValue(dto.getSettingValue());
        entity.setDescription(dto.getDescription());

        return entity;
    }

    public static void updateEntity(Configuration entity, ConfigurationDTO dto) {
        entity.setSettingKey(dto.getSettingKey());
        entity.setSettingValue(dto.getSettingValue());
        entity.setDescription(dto.getDescription());
    }

}