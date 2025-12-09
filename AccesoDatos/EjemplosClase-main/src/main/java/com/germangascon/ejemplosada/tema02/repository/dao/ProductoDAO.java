package com.germangascon.ejemplosada.tema02.repository.dao;

import com.germangascon.ejemplosada.tema02.model.Producto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * <p><strong>ProductoDAO</strong></p>
 * <p>Descripción</p>
 * License: 🅮 Public Domain<br />
 * Created on: 2025-11-13<br />
 *
 * @author Germán Gascón <ggascon@gmail.com>
 * @version 0.0.1
 * @since 0.0.1
 **/
public interface ProductoDAO {
    int insert(Producto producto);
    int update(Producto producto);
    int delete(UUID id);
    Optional<Producto> findById(UUID uuid);
    List<Producto> findAll();
}
