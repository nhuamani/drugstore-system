package com.nhuamani.drugstore_system.controllers;

import com.nhuamani.drugstore_system.dto.UserCreateDTO;
import com.nhuamani.drugstore_system.dto.UserUpdateDTO;
import com.nhuamani.drugstore_system.models.Role;
import com.nhuamani.drugstore_system.models.User;
import com.nhuamani.drugstore_system.repositories.RoleRepository;
import com.nhuamani.drugstore_system.security.CustomUserDetails;
import com.nhuamani.drugstore_system.services.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("pageTitle", "Gestión de usuarios");
        model.addAttribute("users", userService.findAll());

        return "users/index";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("pageTitle", "Crear usuario");
        model.addAttribute("userCreateDTO", new UserCreateDTO(
                "", "", "", "", "", null
        ));
        addRoles(model);

        return "users/create";
    }

    @PostMapping
    public String create(
            @Valid @ModelAttribute("userCreateDTO") UserCreateDTO dto,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Crear usuario");
            addRoles(model);
            return "users/create";
        }

        try {
            userService.create(dto);
        } catch (IllegalArgumentException ex) {
            result.rejectValue("username", "user.duplicate", ex.getMessage());
            model.addAttribute("pageTitle", "Crear usuario");
            addRoles(model);
            return "users/create";
        }

        redirectAttributes.addFlashAttribute(
                "successMessage", "Usuario creado correctamente."
        );

        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {

        User user = userService.findById(id);

        UserUpdateDTO dto = new UserUpdateDTO(
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRoles().stream()
                        .findFirst()
                        .map(Role::getId)
                        .orElse(null)
        );

        model.addAttribute("pageTitle", "Editar usuario");
        model.addAttribute("userId", id);
        model.addAttribute("userUpdateDTO", dto);
        addRoles(model);

        return "users/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(
            @PathVariable Long id,
            @Valid @ModelAttribute("userUpdateDTO") UserUpdateDTO dto,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Editar usuario");
            model.addAttribute("userId", id);
            addRoles(model);
            return "users/edit";
        }

        try {
            userService.update(id, dto);
        } catch (IllegalArgumentException ex) {
            result.rejectValue("username", "user.duplicate", ex.getMessage());
            model.addAttribute("pageTitle", "Editar usuario");
            model.addAttribute("userId", id);
            addRoles(model);
            return "users/edit";
        }

        redirectAttributes.addFlashAttribute(
                "successMessage", "Usuario actualizado correctamente."
        );

        return "redirect:/users";
    }

    @PostMapping("/{id}/status")
    public String changeStatus(
            @PathVariable Long id,
            @RequestParam boolean enabled,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        // Evitar que el administrador deshabilite su propia cuenta.
        Object principal = authentication.getPrincipal();

        if (principal instanceof CustomUserDetails currentUser) {
            User targetUser = userService.findById(id);

            if (!enabled && targetUser.getUsername()
                    .equals(currentUser.getUsername())) {
                redirectAttributes.addFlashAttribute(
                        "errorMessage",
                        "No puedes deshabilitar tu propia cuenta."
                );

                return "redirect:/users";
            }
        }

        userService.changeEnabledStatus(id, enabled);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                enabled
                        ? "Cuenta habilitada correctamente."
                        : "Cuenta deshabilitada correctamente."
        );

        return "redirect:/users";
    }

    private void addRoles(Model model) {
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("roles", roles);
    }
}
