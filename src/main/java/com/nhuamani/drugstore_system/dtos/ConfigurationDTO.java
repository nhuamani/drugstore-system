package com.nhuamani.drugstore_system.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ConfigurationDTO {

    private Integer id;

    private String settingKey;

    private String settingValue;

    private String description;

    // getters y setters
}