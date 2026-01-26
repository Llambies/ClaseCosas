package org.adrian.DAO;

import java.util.List;

import org.adrian.entities.Columna;
import org.adrian.entities.Tarjeta;

public interface ColumnaDAO {
    void crearColumna(Columna columna);
    void actualizarColumnaPorId(Long id, Columna columna);
    void eliminarColumnaPorId(Long id);
    Columna obtenerColumnaPorId(Long id);
    List<Columna> obtenerColumnas();
    List<Tarjeta> obtenerTarjetasPorColumnaId(Long columnaId);
}
