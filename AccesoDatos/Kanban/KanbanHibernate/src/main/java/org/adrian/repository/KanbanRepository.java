package org.adrian.repository;

import java.util.List;

import org.adrian.entities.Columna;
import org.adrian.entities.Etiqueta;
import org.adrian.entities.Tablero;
import org.adrian.entities.Tarjeta;
import org.adrian.entities.Usuario;

public interface KanbanRepository {
    void crearUsuario(Usuario usuario);
    void actualizarUsuarioPorId(Long id, Usuario usuario);
    void eliminarUsuarioPorId(Long id);
    Usuario obtenerUsuarioPorId(Long id);
    Usuario obtenerUsuarioPorEmail(String email);
    List<Usuario> obtenerUsuarios();
    void crearTablero(Tablero tablero);
    void actualizarTableroPorId(Long id, Tablero tablero);
    void eliminarTableroPorId(Long id);
    Tablero obtenerTableroPorId(Long id);
    List<Tablero> obtenerTablerosPorUsuarioId(Long usuarioId);
    List<Columna> obtenerColumnasPorTableroId(Long tableroId);
    List<Tarjeta> obtenerTarjetasPorTableroId(Long tableroId);
    void crearColumna(Columna columna);
    void actualizarColumnaPorId(Long id, Columna columna);
    void eliminarColumnaPorId(Long id);
    Columna obtenerColumnaPorId(Long id);
    List<Columna> obtenerColumnas();
    List<Tarjeta> obtenerTarjetasPorColumnaId(Long columnaId);
    void crearTarjeta(Tarjeta tarjeta);
    void actualizarTarjetaPorId(Long id, Tarjeta tarjeta);
    void eliminarTarjetaPorId(Long id);
    Tarjeta obtenerTarjetaPorId(Long id);
    List<Tarjeta> obtenerTarjetas();
    List<Tarjeta> obtenerTarjetasPorEtiquetaIdYUsuarioId(Long etiquetaId, Long usuarioId);
    void crearEtiqueta(Etiqueta etiqueta);
    void actualizarEtiquetaPorId(Long id, Etiqueta etiqueta);
    void eliminarEtiquetaPorId(Long id);
    Etiqueta obtenerEtiquetaPorId(Long id);
    Etiqueta obtenerEtiquetaPorNombre(String nombre);
    List<Etiqueta> obtenerEtiquetas();
}
