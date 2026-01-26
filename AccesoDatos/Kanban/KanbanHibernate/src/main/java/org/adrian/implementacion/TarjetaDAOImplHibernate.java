package org.adrian.implementacion;

import java.util.List;

import org.adrian.DAO.TarjetaDAO;
import org.adrian.entities.Tarjeta;
import org.hibernate.SessionFactory;

public class TarjetaDAOImplHibernate implements TarjetaDAO {
    private final SessionFactory sessionFactory;

    public TarjetaDAOImplHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void crearTarjeta(Tarjeta tarjeta) {
        sessionFactory.getCurrentSession().persist(tarjeta);
    }

    @Override
    public void actualizarTarjetaPorId(Long id, Tarjeta tarjeta) {
        sessionFactory.getCurrentSession().merge(tarjeta);
    }

    @Override
    public void eliminarTarjetaPorId(Long id) {
        Tarjeta tarjeta = sessionFactory.getCurrentSession().find(Tarjeta.class, id);
        if (tarjeta != null) {
            sessionFactory.getCurrentSession().remove(tarjeta);
        }
    }

    @Override
    public Tarjeta obtenerTarjetaPorId(Long id) {
        return sessionFactory.getCurrentSession().find(Tarjeta.class, id);
    }

    @Override
    public List<Tarjeta> obtenerTarjetas() {
        return sessionFactory.getCurrentSession().createQuery("from Tarjeta", Tarjeta.class).getResultList();
    }

    @Override
    public List<Tarjeta> obtenerTarjetasPorEtiquetaIdYUsuarioId(Long etiquetaId, Long usuarioId) {
        return sessionFactory.getCurrentSession()
                .createQuery("select t from Tarjeta t join t.etiquetas e where e.id = :etiquetaId and t.columna.tablero.usuario.id = :usuarioId", Tarjeta.class)
                .setParameter("etiquetaId", etiquetaId)
                .setParameter("usuarioId", usuarioId)
                .getResultList();
    }
}
