package com.owoma.frasesapi.repository;

import com.owoma.frasesapi.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Long> {
}
