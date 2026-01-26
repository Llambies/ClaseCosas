package org.adrian.DAO;

import java.util.List;

import org.adrian.entities.Etiqueta;

public interface EtiquetaDAO {
    void crearEtiqueta(Etiqueta etiqueta);
    void actualizarEtiquetaPorId(Long id, Etiqueta etiqueta);
    void eliminarEtiquetaPorId(Long id);
    Etiqueta obtenerEtiquetaPorId(Long id);
    Etiqueta obtenerEtiquetaPorNombre(String nombre);
    List<Etiqueta> obtenerEtiquetas();
}
