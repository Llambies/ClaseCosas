package org.adrian.boletin01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BaseDeDatos {
    static String DB_DRIVER;
    static String DB_HOST;
    static String DB_PORT;
    static String DB_USER;
    static String DB_PASSWORD;
    static String DB_NAME;

    Connection conexion;

    public BaseDeDatos(String db_driver,
            String db_host,
            String db_port,
            String db_user,
            String db_password,
            String db_name) {
        DB_DRIVER = db_driver;
        DB_HOST = db_host;
        DB_PORT = db_port;
        DB_USER = db_user;
        DB_PASSWORD = db_password;
        DB_NAME = db_name;
    }

    public BaseDeDatos() {
        this("postgresql",
                "localhost",
                "5434",
                "admin",
                "admin123",
                "ciclismo_db");
    }

    public void conectar() throws SQLException, SQLTimeoutException {
        String url = "jdbc:" + DB_DRIVER + "://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;
        try {
            conexion = DriverManager.getConnection(url, DB_USER, DB_PASSWORD);
            try (Statement stmt = conexion.createStatement()) {
                return;
            } catch (SQLException e) {
                throw new SQLException("Error al conectar a la base de datos: " + e.getMessage());
            }
        } catch (SQLTimeoutException e) {
            throw new SQLTimeoutException("Timeout al conectar a la base de datos: " + e.getMessage());
        } catch (SQLException e) {
            throw new SQLException("Error al conectar a la base de datos: " + e.getMessage());
        }
    }

    public void desconectar() throws SQLException {
        try {
            if (conexion != null) {
                conexion.close();
            }
        } catch (SQLException e) {
            throw new SQLException("Error al desconectar de la base de datos: " + e.getMessage());
        }
    }

    public List<Equipo> listarEquipos() throws SQLException {
        try (Statement stmt = conexion.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM equipos")) {
                List<Equipo> equipos = new ArrayList<>();
                while (rs.next()) {
                    equipos.add(new Equipo(rs.getInt("id_equipo"), rs.getString("nombre"), rs.getString("pais")));
                }
                return equipos;
            }
        } catch (SQLException e) {
            throw new SQLException("Error al listar equipos: " + e.getMessage());
        }
    }

    public List<Ciclista> listarCiclistasPorEquipo(int id_equipo) throws SQLException {
        try (Statement stmt = conexion.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM ciclistas WHERE id_equipo = " + id_equipo)) {
                List<Ciclista> ciclistas = new ArrayList<>();
                while (rs.next()) {
                    ciclistas.add(new Ciclista(rs.getInt("id_ciclista"), rs.getInt("id_equipo"), rs.getString("nombre"),
                            rs.getString("pais"), rs.getDate("fecha_nac")));
                }
                return ciclistas;
            }
        } catch (SQLException e) {
            throw new SQLException("Error al listar ciclistas: " + e.getMessage());
        }
    }

    public List<Etapa> listarEtapas() throws SQLException {
        try (Statement stmt = conexion.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM etapas")) {
                List<Etapa> etapas = new ArrayList<>();
                while (rs.next()) {
                    etapas.add(new Etapa(rs.getInt("id_etapa"), rs.getInt("id_carrera"), rs.getInt("num_etapa"),
                            rs.getDate("fecha"), rs.getString("salida"), rs.getString("llegada"),
                            rs.getDouble("distancia_km"), TipoEtapa.valueOf(rs.getString("tipo"))));
                }
                return etapas;
            }
        } catch (SQLException e) {
            throw new SQLException("Error al listar etapas: " + e.getMessage());
        }
    }

    public List<Carrera> listarCarreras() throws SQLException {
        try (Statement stmt = conexion.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM carreras")) {
                List<Carrera> carreras = new ArrayList<>();
                while (rs.next()) {
                    carreras.add(new Carrera(rs.getInt("id_carrera"), rs.getString("nombre"), rs.getInt("anio"),
                            rs.getDate("fecha_inicio"), rs.getDate("fecha_fin")));
                }
                return carreras;
            }
        } catch (SQLException e) {
            throw new SQLException("Error al listar carreras: " + e.getMessage());
        }
    }

    public List<ResultadosEtapa> listarResultadosEtapa() throws SQLException {
        try (Statement stmt = conexion.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM resultados_etapa")) {
                List<ResultadosEtapa> resultadosEtapa = new ArrayList<>();
                while (rs.next()) {
                    resultadosEtapa.add(new ResultadosEtapa(rs.getInt("id_etapa"), rs.getInt("id_ciclista"),
                            rs.getInt("posicion"), rs.getString("tiempo"), rs.getString("diferencia"),
                            rs.getString("estado")));
                }
                return resultadosEtapa;
            }
        } catch (SQLException e) {
            throw new SQLException("Error al listar resultados de etapa: " + e.getMessage());
        }
    }

    public List<ResultadosSprint> listarResultadosSprint() throws SQLException {
        try (Statement stmt = conexion.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM resultados_sprint")) {
                List<ResultadosSprint> resultadosSprint = new ArrayList<>();
                while (rs.next()) {
                    resultadosSprint.add(new ResultadosSprint(rs.getInt("id_sprint"), rs.getInt("id_ciclista"),
                            rs.getInt("posicion"), rs.getInt("puntos")));
                }
                return resultadosSprint;
            }
        } catch (SQLException e) {
            throw new SQLException("Error al listar resultados de sprint: " + e.getMessage());
        }
    }

    public List<PuntosMeta> listarPuntosMeta() throws SQLException {
        try (Statement stmt = conexion.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM puntos_meta")) {
                List<PuntosMeta> puntosMeta = new ArrayList<>();
                while (rs.next()) {
                    puntosMeta.add(new PuntosMeta(rs.getInt("id_etapa"), rs.getInt("id_ciclista"),
                            rs.getInt("posicion"), rs.getInt("puntos")));
                }
                return puntosMeta;
            }
        } catch (SQLException e) {
            throw new SQLException("Error al listar puntos meta: " + e.getMessage());
        }
    }

    public List<Puerto> listarPuertos() throws SQLException {
        try (Statement stmt = conexion.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM puertos")) {
                List<Puerto> puertos = new ArrayList<>();
                while (rs.next()) {
                    puertos.add(new Puerto(rs.getInt("id_puerto"), rs.getInt("id_etapa"), rs.getString("nombre"),
                            rs.getDouble("km"), rs.getString("categoria")));
                }
                return puertos;
            }
        } catch (SQLException e) {
            throw new SQLException("Error al listar puertos: " + e.getMessage());
        }
    }

    public List<Sprints> listarSprints() throws SQLException {
        try (Statement stmt = conexion.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM sprints")) {
                List<Sprints> sprints = new ArrayList<>();
                while (rs.next()) {
                    sprints.add(new Sprints(rs.getInt("id_sprint"), rs.getInt("id_etapa"), rs.getDouble("km")));
                }
                return sprints;
            }
        } catch (SQLException e) {
            throw new SQLException("Error al listar sprints: " + e.getMessage());
        }
    }

    public List<ResultadosPuerto> listarResultadosPuerto() throws SQLException {
        try (Statement stmt = conexion.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM resultados_puerto")) {
                List<ResultadosPuerto> resultadosPuerto = new ArrayList<>();
                while (rs.next()) {
                    resultadosPuerto.add(new ResultadosPuerto(rs.getInt("id_puerto"), rs.getInt("id_ciclista"),
                            rs.getInt("posicion"), rs.getInt("puntos")));
                }
                return resultadosPuerto;
            }
        } catch (SQLException e) {
            throw new SQLException("Error al listar resultados de puerto: " + e.getMessage());
        }
    }

}
