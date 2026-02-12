package org.adrian.implementacion;

import java.util.List;

import org.adrian.DAO.EtiquetaDAO;
import org.adrian.entities.Etiqueta;
import org.hibernate.SessionFactory;

public class EtiquetaDAOImplHibernate implements EtiquetaDAO {
    private final SessionFactory sessionFactory;

    public EtiquetaDAOImplHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void crearEtiqueta(Etiqueta etiqueta) {
        sessionFactory.getCurrentSession().persist(etiqueta);
    }

    @Override
    public void actualizarEtiquetaPorId(Long id, Etiqueta etiqueta) {
        sessionFactory.getCurrentSession().merge(etiqueta);
    }

    @Override
    public void eliminarEtiquetaPorId(Long id) {
        Etiqueta etiqueta = sessionFactory.getCurrentSession().find(Etiqueta.class, id);
        if (etiqueta != null) {
            sessionFactory.getCurrentSession().remove(etiqueta);
        }
    }

    @Override
    public Etiqueta obtenerEtiquetaPorId(Long id) {
        return sessionFactory.getCurrentSession().find(Etiqueta.class, id);
    }

    @Override
    public Etiqueta obtenerEtiquetaPorNombre(String nombre) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Etiqueta where nombre = :nombre", Etiqueta.class)
                .setParameter("nombre", nombre)
                .uniqueResult();
    }

    @Override
    public List<Etiqueta> obtenerEtiquetas() {
        return sessionFactory.getCurrentSession().createQuery("from Etiqueta", Etiqueta.class).getResultList();
    }
}
