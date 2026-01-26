package org.adrian.DAO;

import java.util.List;

import org.adrian.entities.Columna;
import org.adrian.entities.Tablero;
import org.adrian.entities.Tarjeta;

public interface TableroDAO {
    void crearTablero(Tablero tablero);
    void actualizarTableroPorId(Long id, Tablero tablero);
    void eliminarTableroPorId(Long id);
    Tablero obtenerTableroPorId(Long id);
    List<Tablero> obtenerTablerosPorUsuarioId(Long usuarioId);
    List<Columna> obtenerColumnasPorTableroId(Long tableroId);
    List<Tarjeta> obtenerTarjetasPorTableroId(Long tableroId);  
}
