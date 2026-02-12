package org.adrian.DAO;

import java.util.List;

import org.adrian.entities.Tarjeta;

public interface TarjetaDAO {
    void crearTarjeta(Tarjeta tarjeta);
    void actualizarTarjetaPorId(Long id, Tarjeta tarjeta);
    void eliminarTarjetaPorId(Long id);
    Tarjeta obtenerTarjetaPorId(Long id);
    List<Tarjeta> obtenerTarjetas();
    List<Tarjeta> obtenerTarjetasPorEtiquetaIdYUsuarioId(Long etiquetaId, Long usuarioId);
}
