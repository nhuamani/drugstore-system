package com.nhuamani.drugstore_system.controllers;

import com.nhuamani.drugstore_system.dtos.ConfigurationFormDTO;
import com.nhuamani.drugstore_system.services.ConfigurationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/configurations")
public class ConfigurationController {

    private final ConfigurationService configurationService;

    public ConfigurationController(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    // Mostrar formulario de configuración
    @GetMapping
    public String showConfiguration(Model model) {

        ConfigurationFormDTO configuration = configurationService.getConfiguration();

        model.addAttribute("configuration", configuration);
        model.addAttribute("title", "Ajustes de Empresa");
        model.addAttribute("singular_name", "Configuración");
        model.addAttribute("name_plural", "Configuraciones");

        return "configuration/settings";
    }


    // Guardar cambios
    @PostMapping("/save")
    public String saveConfiguration(
            @ModelAttribute("configuration") ConfigurationFormDTO dto,
            @RequestParam("logoFile") MultipartFile logoFile,
            RedirectAttributes redirectAttributes) {

        configurationService.saveConfiguration(dto, logoFile);

        redirectAttributes.addFlashAttribute(
                "success",
                "Configuración actualizada correctamente"
        );
        return "redirect:/configurations";
    }

}