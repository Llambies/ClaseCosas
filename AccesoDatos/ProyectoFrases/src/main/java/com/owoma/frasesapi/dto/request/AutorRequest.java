package com.owoma.frasesapi.dto.request;

import com.owoma.frasesapi.validation.Year4Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AutorRequest(
        @NotBlank(message = "el nombre es obligatorio")
        @Size(max = 120, message = "el nombre no puede superar 120 caracteres")
        String nombre,

        @NotNull(message = "el anio de nacimiento es obligatorio")
        @Year4Digits
        Integer anioNacimiento,

        @Year4Digits
        Integer anioFallecimiento,

        @Size(max = 120, message = "la profesion no puede superar 120 caracteres")
        String profesion
) {
}
