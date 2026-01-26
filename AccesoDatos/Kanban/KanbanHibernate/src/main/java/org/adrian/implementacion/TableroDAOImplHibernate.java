package org.adrian.implementacion;

import java.util.List;

import org.adrian.DAO.TableroDAO;
import org.adrian.entities.Columna;
import org.adrian.entities.Tablero;
import org.adrian.entities.Tarjeta;
import org.hibernate.SessionFactory;

public class TableroDAOImplHibernate implements TableroDAO {
    private final SessionFactory sessionFactory;

    public TableroDAOImplHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void crearTablero(Tablero tablero) {
        sessionFactory.getCurrentSession().persist(tablero);
    }

    @Override
    public void actualizarTableroPorId(Long id, Tablero tablero) {
        sessionFactory.getCurrentSession().merge(tablero);
    }

    @Override
    public void eliminarTableroPorId(Long id) {
        Tablero tablero = sessionFactory.getCurrentSession().find(Tablero.class, id);
        if (tablero != null) {
            sessionFactory.getCurrentSession().remove(tablero);
        }
    }

    @Override
    public Tablero obtenerTableroPorId(Long id) {
        return sessionFactory.getCurrentSession().find(Tablero.class, id);
    }

    @Override
    public List<Tablero> obtenerTablerosPorUsuarioId(Long usuarioId) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Tablero where usuario.id = :usuarioId", Tablero.class)
                .setParameter("usuarioId", usuarioId)
                .getResultList();
    }

    @Override
    public List<Columna> obtenerColumnasPorTableroId(Long tableroId) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Columna where tablero.id = :tableroId", Columna.class)
                .setParameter("tableroId", tableroId)
                .getResultList();
    }

    @Override
    public List<Tarjeta> obtenerTarjetasPorTableroId(Long tableroId) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Tarjeta where columna.tablero.id = :tableroId", Tarjeta.class)
                .setParameter("tableroId", tableroId)
                .getResultList();
    }
}
