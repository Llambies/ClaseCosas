package org.adrian.implementacion;

import java.util.List;
import java.util.function.Supplier;

import org.adrian.DAO.ColumnaDAO;
import org.adrian.DAO.EtiquetaDAO;
import org.adrian.DAO.TableroDAO;
import org.adrian.DAO.TarjetaDAO;
import org.adrian.DAO.UsuarioDAO;
import org.adrian.entities.Columna;
import org.adrian.entities.Etiqueta;
import org.adrian.entities.Tablero;
import org.adrian.entities.Tarjeta;
import org.adrian.entities.Usuario;
import org.adrian.repository.KanbanRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class KanbanRepositoryImplHibernate implements KanbanRepository {
    private final SessionFactory sessionFactory;
    private final UsuarioDAO usuarioDAO;
    private final TableroDAO tableroDAO;
    private final ColumnaDAO columnaDAO;
    private final TarjetaDAO tarjetaDAO;
    private final EtiquetaDAO etiquetaDAO;

    public KanbanRepositoryImplHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.usuarioDAO = new UsuarioDAOImplHibernate(sessionFactory);
        this.tableroDAO = new TableroDAOImplHibernate(sessionFactory);
        this.columnaDAO = new ColumnaDAOImplHibernate(sessionFactory);
        this.tarjetaDAO = new TarjetaDAOImplHibernate(sessionFactory);
        this.etiquetaDAO = new EtiquetaDAOImplHibernate(sessionFactory);
    }

    private void executeVoid(Runnable action) {
        Transaction tx = null;
        try {
            Session session = sessionFactory.getCurrentSession();
            tx = session.beginTransaction();
            action.run();
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }

    private <T> T executeReturn(Supplier<T> action) {
        Transaction tx = null;
        try {
            Session session = sessionFactory.getCurrentSession();
            tx = session.beginTransaction();
            T result = action.get();
            tx.commit();
            return result;
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public void crearUsuario(Usuario usuario) {
        executeVoid(() -> usuarioDAO.crearUsuario(usuario));
    }

    @Override
    public void actualizarUsuarioPorId(Long id, Usuario usuario) {
        executeVoid(() -> usuarioDAO.actualizarUsuarioPorId(id, usuario));
    }

    @Override
    public void eliminarUsuarioPorId(Long id) {
        executeVoid(() -> usuarioDAO.eliminarUsuarioPorId(id));
    }

    @Override
    public Usuario obtenerUsuarioPorId(Long id) {
        return executeReturn(() -> usuarioDAO.obtenerUsuarioPorId(id));
    }

    @Override
    public Usuario obtenerUsuarioPorEmail(String email) {
        return executeReturn(() -> usuarioDAO.obtenerUsuarioPorEmail(email));
    }

    @Override
    public List<Usuario> obtenerUsuarios() {
        return executeReturn(() -> usuarioDAO.obtenerUsuarios());
    }

    @Override
    public void crearTablero(Tablero tablero) {
        executeVoid(() -> {
            // Crear el tablero
            tableroDAO.crearTablero(tablero);
            
            // Crear las 3 columnas por defecto: TODO, DOING, DONE
            Columna columnaTodo = new Columna("TODO", tablero);
            Columna columnaDoing = new Columna("DOING", tablero);
            Columna columnaDone = new Columna("DONE", tablero);
            
            columnaDAO.crearColumna(columnaTodo);
            columnaDAO.crearColumna(columnaDoing);
            columnaDAO.crearColumna(columnaDone);
        });
    }

    @Override
    public void actualizarTableroPorId(Long id, Tablero tablero) {
        executeVoid(() -> tableroDAO.actualizarTableroPorId(id, tablero));
    }

    @Override
    public void eliminarTableroPorId(Long id) {
        executeVoid(() -> tableroDAO.eliminarTableroPorId(id));
    }

    @Override
    public Tablero obtenerTableroPorId(Long id) {
        return executeReturn(() -> tableroDAO.obtenerTableroPorId(id));
    }

    @Override
    public List<Tablero> obtenerTablerosPorUsuarioId(Long usuarioId) {
        return executeReturn(() -> tableroDAO.obtenerTablerosPorUsuarioId(usuarioId));
    }

    @Override
    public List<Columna> obtenerColumnasPorTableroId(Long tableroId) {
        return executeReturn(() -> tableroDAO.obtenerColumnasPorTableroId(tableroId));
    }

    @Override
    public List<Tarjeta> obtenerTarjetasPorTableroId(Long tableroId) {
        return executeReturn(() -> tableroDAO.obtenerTarjetasPorTableroId(tableroId));
    }

    @Override
    public void crearColumna(Columna columna) {
        executeVoid(() -> columnaDAO.crearColumna(columna));
    }

    @Override
    public void actualizarColumnaPorId(Long id, Columna columna) {
        executeVoid(() -> columnaDAO.actualizarColumnaPorId(id, columna));
    }

    @Override
    public void eliminarColumnaPorId(Long id) {
        executeVoid(() -> columnaDAO.eliminarColumnaPorId(id));
    }

    @Override
    public Columna obtenerColumnaPorId(Long id) {
        return executeReturn(() -> columnaDAO.obtenerColumnaPorId(id));
    }

    @Override
    public List<Columna> obtenerColumnas() {
        return executeReturn(() -> columnaDAO.obtenerColumnas());
    }

    @Override
    public List<Tarjeta> obtenerTarjetasPorColumnaId(Long columnaId) {
        return executeReturn(() -> columnaDAO.obtenerTarjetasPorColumnaId(columnaId));
    }

    @Override
    public void crearTarjeta(Tarjeta tarjeta) {
        executeVoid(() -> tarjetaDAO.crearTarjeta(tarjeta));
    }

    @Override
    public void actualizarTarjetaPorId(Long id, Tarjeta tarjeta) {
        executeVoid(() -> tarjetaDAO.actualizarTarjetaPorId(id, tarjeta));
    }

    @Override
    public void eliminarTarjetaPorId(Long id) {
        executeVoid(() -> tarjetaDAO.eliminarTarjetaPorId(id));
    }

    @Override
    public Tarjeta obtenerTarjetaPorId(Long id) {
        return executeReturn(() -> tarjetaDAO.obtenerTarjetaPorId(id));
    }

    @Override
    public List<Tarjeta> obtenerTarjetas() {
        return executeReturn(() -> tarjetaDAO.obtenerTarjetas());
    }

    @Override
    public List<Tarjeta> obtenerTarjetasPorEtiquetaIdYUsuarioId(Long etiquetaId, Long usuarioId) {
        return executeReturn(() -> tarjetaDAO.obtenerTarjetasPorEtiquetaIdYUsuarioId(etiquetaId, usuarioId));
    }

    @Override
    public void crearEtiqueta(Etiqueta etiqueta) {
        executeVoid(() -> etiquetaDAO.crearEtiqueta(etiqueta));
    }

    @Override
    public void actualizarEtiquetaPorId(Long id, Etiqueta etiqueta) {
        executeVoid(() -> etiquetaDAO.actualizarEtiquetaPorId(id, etiqueta));
    }

    @Override
    public void eliminarEtiquetaPorId(Long id) {
        executeVoid(() -> etiquetaDAO.eliminarEtiquetaPorId(id));
    }

    @Override
    public Etiqueta obtenerEtiquetaPorId(Long id) {
        return executeReturn(() -> etiquetaDAO.obtenerEtiquetaPorId(id));
    }

    @Override
    public Etiqueta obtenerEtiquetaPorNombre(String nombre) {
        return executeReturn(() -> etiquetaDAO.obtenerEtiquetaPorNombre(nombre));
    }

    @Override
    public List<Etiqueta> obtenerEtiquetas() {
        return executeReturn(() -> etiquetaDAO.obtenerEtiquetas());
    }
}
