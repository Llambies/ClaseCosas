package org.adrian.implementacion;

import java.util.List;

import org.adrian.DAO.UsuarioDAO;
import org.adrian.entities.Usuario;
import org.hibernate.SessionFactory;

public class UsuarioDAOImplHibernate implements UsuarioDAO {
    private final SessionFactory sessionFactory;

    public UsuarioDAOImplHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void crearUsuario(Usuario usuario) {
        sessionFactory.getCurrentSession().persist(usuario);
    }

    @Override
    public void actualizarUsuarioPorId(Long id, Usuario usuario) {
        sessionFactory.getCurrentSession().merge(usuario);
    }

    @Override
    public void eliminarUsuarioPorId(Long id) {
        Usuario usuario = sessionFactory.getCurrentSession().find(Usuario.class, id);
        if (usuario != null) {
            sessionFactory.getCurrentSession().remove(usuario);
        }
    }

    @Override
    public Usuario obtenerUsuarioPorId(Long id) {
        return sessionFactory.getCurrentSession().find(Usuario.class, id);
    }

    @Override
    public Usuario obtenerUsuarioPorEmail(String email) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Usuario where email = :email", Usuario.class)
                .setParameter("email", email)
                .uniqueResult();
    }

    @Override
    public List<Usuario> obtenerUsuarios() {
        return sessionFactory.getCurrentSession().createQuery("from Usuario", Usuario.class).getResultList();
    }
}
