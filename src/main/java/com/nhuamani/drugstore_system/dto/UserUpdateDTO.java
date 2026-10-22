package com.nhuamani.drugstore_system.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserUpdateDTO(

        @NotBlank(message = "El nombre de usuario es obligatorio")
        @Size(max = 50)
        String username,

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 50)
        String firstName,

        @NotBlank(message = "Los apellidos son obligatorios")
        @Size(max = 50)
        String lastName,

        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "Introduce un correo válido")
        @Size(max = 100)
        String email,

        @NotNull(message = "Selecciona un rol")
        Long roleId

) {
}

