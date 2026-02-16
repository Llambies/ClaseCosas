package com.owoma.frasesapi.dto.response;

public record AutorResponse(
        Long id,
        String nombre,
        Integer anioNacimiento,
        Integer anioFallecimiento,
        String profesion
) {
}
