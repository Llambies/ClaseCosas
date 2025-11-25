package org.adrian.ejemplo01.DAOs;

import org.adrian.ejemplo01.model.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoDAO {
    int Insert(Producto producto);

    int Update(Producto producto);

    int Delete(String id);

    Optional<Producto> findById(String id);

    List<Producto> findAll();
}
