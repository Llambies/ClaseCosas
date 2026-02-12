package org.adrian.controladores;

import java.util.List;

import org.adrian.entities.Columna;
import org.adrian.entities.Etiqueta;
import org.adrian.entities.Tablero;
import org.adrian.entities.Tarjeta;
import org.adrian.entities.Usuario;
import org.adrian.repository.KanbanRepository;
import org.adrian.view.Menu;
import org.adrian.view.MenuAutenticacion;

public class App {
    private final KanbanRepository kanbanRepository;
    private Usuario usuarioAutenticado;

    public App( KanbanRepository kanbanRepository) {
        this.kanbanRepository = kanbanRepository;
    }

    public void iniciar() {
        boolean continuar = true;
        
        while (continuar) {
            // Mostrar menú de autenticación
            MenuAutenticacion menuAutenticacion = new MenuAutenticacion(this);
            usuarioAutenticado = menuAutenticacion.mostrarMenu();
            
            // Si el usuario se autenticó correctamente, mostrar el menú principal
            if (usuarioAutenticado != null) {
                Menu menu = new Menu(this);
                menu.iniciar();
                // Después del logout, el usuarioAutenticado será null y volverá al menú de autenticación
            } else {
                continuar = false; // El usuario eligió salir
            }
        }
    }

    public Usuario obtenerUsuarioPorEmail(String email) {
        return kanbanRepository.obtenerUsuarioPorEmail(email);
    }

    public void crearUsuario(Usuario usuario) {
        kanbanRepository.crearUsuario(usuario);
    }

    public Usuario getUsuarioAutenticado() {
        return usuarioAutenticado;
    }

    public void logout() {
        usuarioAutenticado = null;
    }

    // ========== TABLEROS ==========
    public void crearTablero(Tablero tablero) {
        kanbanRepository.crearTablero(tablero);
    }

    public List<Tablero> obtenerTablerosPorUsuarioId(Long usuarioId) {
        return kanbanRepository.obtenerTablerosPorUsuarioId(usuarioId);
    }

    public Tablero obtenerTableroPorId(Long id) {
        return kanbanRepository.obtenerTableroPorId(id);
    }

    public void eliminarTableroPorId(Long id) {
        kanbanRepository.eliminarTableroPorId(id);
    }

    // ========== COLUMNAS ==========
    public void crearColumna(Columna columna) {
        kanbanRepository.crearColumna(columna);
    }

    public List<Columna> obtenerColumnasPorTableroId(Long tableroId) {
        return kanbanRepository.obtenerColumnasPorTableroId(tableroId);
    }

    public Columna obtenerColumnaPorId(Long id) {
        return kanbanRepository.obtenerColumnaPorId(id);
    }

    public void actualizarColumnaPorId(Long id, Columna columna) {
        kanbanRepository.actualizarColumnaPorId(id, columna);
    }

    public void eliminarColumnaPorId(Long id) {
        kanbanRepository.eliminarColumnaPorId(id);
    }

    // ========== TARJETAS ==========
    public void crearTarjeta(Tarjeta tarjeta) {
        kanbanRepository.crearTarjeta(tarjeta);
    }

    public List<Tarjeta> obtenerTarjetasPorColumnaId(Long columnaId) {
        return kanbanRepository.obtenerTarjetasPorColumnaId(columnaId);
    }

    public List<Tarjeta> obtenerTarjetasPorTableroId(Long tableroId) {
        return kanbanRepository.obtenerTarjetasPorTableroId(tableroId);
    }

    public Tarjeta obtenerTarjetaPorId(Long id) {
        return kanbanRepository.obtenerTarjetaPorId(id);
    }

    public void actualizarTarjetaPorId(Long id, Tarjeta tarjeta) {
        kanbanRepository.actualizarTarjetaPorId(id, tarjeta);
    }

    public void eliminarTarjetaPorId(Long id) {
        kanbanRepository.eliminarTarjetaPorId(id);
    }

    public List<Tarjeta> obtenerTarjetasPorEtiquetaIdYUsuarioId(Long etiquetaId, Long usuarioId) {
        return kanbanRepository.obtenerTarjetasPorEtiquetaIdYUsuarioId(etiquetaId, usuarioId);
    }

    // ========== ETIQUETAS ==========
    public void crearEtiqueta(Etiqueta etiqueta) {
        kanbanRepository.crearEtiqueta(etiqueta);
    }

    public List<Etiqueta> obtenerEtiquetas() {
        return kanbanRepository.obtenerEtiquetas();
    }

    public Etiqueta obtenerEtiquetaPorId(Long id) {
        return kanbanRepository.obtenerEtiquetaPorId(id);
    }

    public Etiqueta obtenerEtiquetaPorNombre(String nombre) {
        return kanbanRepository.obtenerEtiquetaPorNombre(nombre);
    }

    // ========== MÉTODOS ANTIGUOS (para compatibilidad) ==========
    public List<Usuario> obtenerUsuarios() {
        return kanbanRepository.obtenerUsuarios();
    }

    public List<Tarjeta> obtenerTarjetas() {
        return kanbanRepository.obtenerTarjetas();
    }

    public List<Columna> obtenerColumnas() {
        return kanbanRepository.obtenerColumnas();
    }
}
