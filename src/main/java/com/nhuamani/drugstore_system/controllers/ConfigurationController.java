package com.nhuamani.drugstore_system.controllers;

import com.nhuamani.drugstore_system.dtos.ConfigurationDTO;
import com.nhuamani.drugstore_system.services.ConfigurationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/configurations")
public class ConfigurationController {

    private final ConfigurationService service;

    public ConfigurationController(ConfigurationService service){
        this.service = service;
    }

    @GetMapping
    public String getAll(Model model) {

        model.addAttribute("configurations", service.findAll());

        return "configuration/list";
    }

    @GetMapping("/new")
    public String newConfiguration(Model model) {

        model.addAttribute("configuration", new ConfigurationDTO());

        return "configuration/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute ConfigurationDTO dto){

        service.save(dto);

        return "redirect:/configurations";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        ConfigurationDTO dto = service.findById(id);

        if (dto == null) {
            return "redirect:/configurations";
        }

        model.addAttribute("configuration", dto);
        return "configuration/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "redirect:/configurations";
    }

}