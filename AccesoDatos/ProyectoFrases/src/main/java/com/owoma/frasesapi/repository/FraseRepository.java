package com.owoma.frasesapi.repository;

import com.owoma.frasesapi.model.Frase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface FraseRepository extends JpaRepository<Frase, Long> {
    Page<Frase> findByAutorId(Long autorId, Pageable pageable);

    Page<Frase> findByCategoriaId(Long categoriaId, Pageable pageable);

    Optional<Frase> findByFechaProgramada(LocalDate fechaProgramada);
}
