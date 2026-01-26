package org.adrian.implementacion;

import java.util.List;

import org.adrian.DAO.ColumnaDAO;
import org.adrian.entities.Columna;
import org.adrian.entities.Tarjeta;
import org.hibernate.SessionFactory;

public class ColumnaDAOImplHibernate implements ColumnaDAO {
    private final SessionFactory sessionFactory;

    public ColumnaDAOImplHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void crearColumna(Columna columna) {
        sessionFactory.getCurrentSession().persist(columna);
    }

    @Override
    public void actualizarColumnaPorId(Long id, Columna columna) {
        sessionFactory.getCurrentSession().merge(columna);
    }

    @Override
    public void eliminarColumnaPorId(Long id) {
        Columna columna = sessionFactory.getCurrentSession().find(Columna.class, id);
        if (columna != null) {
            sessionFactory.getCurrentSession().remove(columna);
        }
    }

    @Override
    public Columna obtenerColumnaPorId(Long id) {
        return sessionFactory.getCurrentSession().find(Columna.class, id);
    }

    @Override
    public List<Columna> obtenerColumnas() {
        return sessionFactory.getCurrentSession().createQuery("from Columna", Columna.class).getResultList();
    }

    @Override
    public List<Tarjeta> obtenerTarjetasPorColumnaId(Long columnaId) {
        return sessionFactory.getCurrentSession().createQuery("from Tarjeta where columna.id = :columnaId", Tarjeta.class)
                .setParameter("columnaId", columnaId)
                .getResultList();
    }
}
