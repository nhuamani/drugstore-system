package com.nhuamani.drugstore_system.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserCreateDTO(

        @NotBlank(message = "El nombre de usuario es obligatorio")
        @Size(max = 50, message = "Máximo 50 caracteres")
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

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 8, max = 72, message = "La contraseña debe tener entre 8 y 72 caracteres")
        String password,

        @NotNull(message = "Selecciona un rol")
        Long roleId

) {
}
