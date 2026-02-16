package com.owoma.frasesapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record FraseRequest(
        @NotBlank(message = "el texto es obligatorio")
        @Size(min = 5, max = 500, message = "el texto debe tener entre 5 y 500 caracteres")
        String texto,

        @NotNull(message = "la fecha programada es obligatoria")
        LocalDate fechaProgramada,

        @NotNull(message = "el autorId es obligatorio")
        Long autorId,

        @NotNull(message = "el categoriaId es obligatorio")
        Long categoriaId
) {
}
