package com.nhuamani.drugstore_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDTO {

    private Long id;

    @NotBlank(message = "El nombre de la categoría es obligatorio.")
    @Size(
            min = 2,
            max = 100,
            message = "El nombre debe tener entre 2 y 100 caracteres."
    )
    private String name;

    @Size(
            max = 255,
            message = "La descripción no puede superar los 255 caracteres."
    )
    private String description;
    private Long parentId;
    private boolean enabled = true;
}
