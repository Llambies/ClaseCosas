package org.adrian.ejemplo01.DAOs;

import java.util.List;
import java.util.Optional;

import org.adrian.ejemplo01.model.Partido;

public interface PartidoDAO {
    List<Partido> findAll();

    Optional<Partido> findById(int id);

    List<Partido> findByJornada(int numeroJornada);
}
