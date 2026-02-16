package com.owoma.frasesapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaRequest(
        @NotBlank(message = "el nombre es obligatorio")
        @Size(max = 80, message = "el nombre no puede superar 80 caracteres")
        String nombre
) {
}
