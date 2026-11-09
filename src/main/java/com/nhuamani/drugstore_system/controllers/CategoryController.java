package com.nhuamani.drugstore_system.controllers;

import com.nhuamani.drugstore_system.dto.CategoryDTO;
import com.nhuamani.drugstore_system.models.Category;
import com.nhuamani.drugstore_system.services.CategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // Listar categorías
    @GetMapping
    public String index(Model model) {

        model.addAttribute("pageTitle", "Gestión de categorías");
        model.addAttribute("categories", categoryService.findAll());

        return "categories/index";
    }

    // Mostrar formulario de creación
    @GetMapping("/create")
    public String create(Model model) {

        model.addAttribute("pageTitle", "Crear categoría");
        model.addAttribute("categoryDTO", new CategoryDTO());
        loadParentCategories(model, null);

        return "categories/create";
    }

    // Registrar categoría
    @PostMapping
    public String store(
            @Valid @ModelAttribute("categoryDTO") CategoryDTO dto,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes
    ) {

        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Crear categoría");
            loadParentCategories(model, null);
            return "categories/create";
        }

        try {
            categoryService.create(
                    dto.getName(),
                    dto.getDescription(),
                    dto.getParentId(),
                    dto.isEnabled()
            );

            redirectAttributes.addFlashAttribute(
                    "success",
                    "Categoría registrada correctamente."
            );

            return "redirect:/categories";

        } catch (IllegalArgumentException e) {

            result.reject("category.error", e.getMessage());

            model.addAttribute("pageTitle", "Crear categoría");
            loadParentCategories(model, null);

            return "categories/create";
        }
    }

    // Mostrar formulario de edición
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {

        Category category = categoryService.findById(id);

        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setParentId(
                category.getParent() != null
                        ? category.getParent().getId()
                        : null
        );
        dto.setEnabled(category.isEnabled());

        model.addAttribute("pageTitle", "Editar categoría");
        model.addAttribute("categoryDTO", dto);

        loadParentCategories(model, id);

        return "categories/edit";
    }

    // Actualizar categoría
    @PostMapping("/edit/{id}")
    public String update(
            @PathVariable Long id,
            @Valid @ModelAttribute("categoryDTO") CategoryDTO dto,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes
    ) {

        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Editar categoría");
            loadParentCategories(model, id);
            return "categories/edit";
        }

        try {
            categoryService.update(
                    id,
                    dto.getName(),
                    dto.getDescription(),
                    dto.getParentId(),
                    dto.isEnabled()
            );

            redirectAttributes.addFlashAttribute(
                    "success",
                    "Categoría actualizada correctamente."
            );

            return "redirect:/categories";

        } catch (IllegalArgumentException e) {

            result.reject("category.error", e.getMessage());

            model.addAttribute("pageTitle", "Editar categoría");
            loadParentCategories(model, id);

            return "categories/edit";
        }
    }

    // Desactivar categoría
    @PostMapping("/{id}/status")
    public String disable(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        try {
            categoryService.disable(id);

            redirectAttributes.addFlashAttribute(
                    "success",
                    "Categoría desactivada correctamente."
            );

        } catch (IllegalArgumentException | IllegalStateException e) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    e.getMessage()
            );
        }

        return "redirect:/categories";
    }

    // Cargar categorías disponibles para seleccionar padre
    private void loadParentCategories(Model model, Long currentId) {

        List<Category> parentCategories;

        if (currentId == null) {
            parentCategories = categoryService.findAll()
                    .stream()
                    .filter(Category::isEnabled)
                    .toList();
        } else {
            parentCategories = categoryService.findAvailableParents(currentId);
        }

        model.addAttribute("parentCategories", parentCategories);
    }
}
