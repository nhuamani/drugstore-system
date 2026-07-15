package com.nhuamani.drugstore_system.services.impl;

import com.nhuamani.drugstore_system.dtos.ConfigurationFormDTO;
import com.nhuamani.drugstore_system.models.Configuration;
import com.nhuamani.drugstore_system.repositories.ConfigurationRepository;
import com.nhuamani.drugstore_system.services.ConfigurationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ConfigurationServiceImpl implements ConfigurationService {

    private final ConfigurationRepository configurationRepository;

    public ConfigurationServiceImpl(ConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ConfigurationFormDTO getConfiguration() {

        ConfigurationFormDTO dto = new ConfigurationFormDTO();

        dto.setNombreBotica(getValue("nombre_botica"));
        dto.setRuc(getValue("ruc"));
        dto.setDireccion(getValue("direccion"));
        dto.setTelefono(getValue("telefono"));
        dto.setMoneda(getValue("moneda"));
        dto.setLogo(getValue("logo"));

        String igv = getValue("igv");

        if (igv != null && !igv.isBlank()) {
            dto.setIgv(Integer.valueOf(igv));
        }

        return dto;
    }

    @Override
    public void saveConfiguration(ConfigurationFormDTO dto) {

        updateValue("nombre_botica", dto.getNombreBotica());
        updateValue("ruc", dto.getRuc());
        updateValue("direccion", dto.getDireccion());
        updateValue("telefono", dto.getTelefono());
        updateValue("moneda", dto.getMoneda());
        updateValue("logo", dto.getLogo());

        if (dto.getIgv() != null) {
            updateValue("igv", dto.getIgv().toString());
        }

    }

    /**
     * Obtiene el valor de una configuración.
     */
    private String getValue(String key) {

        return configurationRepository
                .findBySettingKey(key)
                .map(Configuration::getSettingValue)
                .orElse("");
    }

    /**
     * Actualiza el valor de una configuración existente.
     */
    private void updateValue(String key, String value) {

        Optional<Configuration> result = configurationRepository.findBySettingKey(key);

        if(result.isPresent()) {
            Configuration configuration = result.get();

            System.out.println("Actualizando: " + configuration.getSettingKey() + " = " + value);

            configuration.setSettingValue(value);
            configurationRepository.save(configuration);
        } else {
            System.out.println("No encontrada: " + key);
        }

    }

}