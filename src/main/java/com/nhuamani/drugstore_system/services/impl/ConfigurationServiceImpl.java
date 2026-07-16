package com.nhuamani.drugstore_system.services.impl;

import com.nhuamani.drugstore_system.dtos.ConfigurationFormDTO;
import com.nhuamani.drugstore_system.models.Configuration;
import com.nhuamani.drugstore_system.repositories.ConfigurationRepository;
import com.nhuamani.drugstore_system.services.ConfigurationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
@Transactional
public class ConfigurationServiceImpl implements ConfigurationService {

    private static final String UPLOAD_FOLDER = "src/main/resources/static/uploads/logos/";
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
    public void saveConfiguration(ConfigurationFormDTO dto, MultipartFile logoFile) {

        if (!logoFile.isEmpty()) {

            try {
                String originalFilename = logoFile.getOriginalFilename();

                // Obtener la extensión (.png, .jpg, .jpeg, .webp)
                String extension = "";

                if (originalFilename != null && originalFilename.contains(".")) {
                    extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                }

                // Generar un nombre único
                String fileName = "farmacia_" + System.currentTimeMillis() + extension;

                // Crear la carpeta si no existe
                Path uploadPath = Paths.get(UPLOAD_FOLDER);
                Files.createDirectories(uploadPath);

                // Guardar el archivo
                Path filePath = Paths.get(UPLOAD_FOLDER).resolve(fileName);
                Files.copy(
                        logoFile.getInputStream(),
                        filePath,
                        StandardCopyOption.REPLACE_EXISTING
                );

                // Guardar solo el nombre en la BD
                dto.setLogo(fileName);
            } catch (IOException e) {
                throw new RuntimeException("Error guardando logo", e);
            }
        }

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