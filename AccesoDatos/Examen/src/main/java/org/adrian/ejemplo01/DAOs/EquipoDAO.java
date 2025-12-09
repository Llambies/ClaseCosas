package org.adrian.ejemplo01.DAOs;

import java.util.List;
import java.util.Optional;

import org.adrian.ejemplo01.model.Equipo;

public interface EquipoDAO {
    List<Equipo> findAll();

    Optional<Equipo> findById(int id);

    List<Equipo> findByNombre(String nombre);
}
