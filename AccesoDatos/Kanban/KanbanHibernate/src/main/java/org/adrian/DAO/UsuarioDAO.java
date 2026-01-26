package org.adrian.DAO;

import java.util.List;

import org.adrian.entities.Usuario;

public interface UsuarioDAO {
    void crearUsuario(Usuario usuario);
    void actualizarUsuarioPorId(Long id, Usuario usuario);
    void eliminarUsuarioPorId(Long id);
    Usuario obtenerUsuarioPorId(Long id);
    Usuario obtenerUsuarioPorEmail(String email);
    List<Usuario> obtenerUsuarios();
}
