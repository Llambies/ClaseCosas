package com.owoma.frasesapi.dto.response;

import java.time.LocalDate;

public record FraseResponse(
        Long id,
        String texto,
        LocalDate fechaProgramada,
        Long autorId,
        String autorNombre,
        Long categoriaId,
        String categoriaNombre
) {
}
