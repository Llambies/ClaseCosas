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

    public List<String[]> obtenerEquipos() throws SQLException {
        List<String[]> equipos = new ArrayList<>();
        try (Statement stmt = conexion.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("""
                    SELECT * FROM equipos
                    """)) {
                while (rs.next()) {
                    equipos.add(new String[] { String.valueOf(rs.getInt("id_equipo")),
                            rs.getString("nombre"), rs.getString("pais") });
                }
            } catch (SQLException e) {
                throw new SQLException("Error al obtener equipos: " + e.getMessage());
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener equipos: " + e.getMessage());
        }
        return equipos;
    }

    public List<String[]> obtenerCiclistasPorEquipo(int id_equipo) throws SQLException {
        List<String[]> ciclistas = new ArrayList<>();
        try (Statement stmt = conexion.createStatement()) {
            String query;
            if (id_equipo == 0) {
                query = """
                        SELECT ciclistas.id_ciclista, ciclistas.nombre, ciclistas.pais, equipos.nombre AS nombre_equipo
                        FROM ciclistas
                        JOIN equipos ON ciclistas.id_equipo = equipos.id_equipo
                        ORDER BY equipos.nombre, ciclistas.nombre
                        """;
            } else {
                query = """
                        SELECT id_ciclista, nombre, pais, equipos.nombre AS nombre_equipo FROM ciclistas
                        JOIN equipos ON ciclistas.id_equipo = equipos.id_equipo
                        WHERE id_equipo = %d
                        """.formatted(id_equipo);
            }
            try (ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    if (id_equipo == 0) {
                        ciclistas.add(new String[] { String.valueOf(rs.getInt("id_ciclista")),
                                rs.getString("nombre"), rs.getString("pais"), rs.getString("nombre_equipo") });
                    } else {
                        ciclistas.add(new String[] { String.valueOf(rs.getInt("id_ciclista")),
                                rs.getString("nombre"), rs.getString("pais"), rs.getString("nombre_equipo") });
                    }
                }
            } catch (SQLException e) {
                throw new SQLException("Error al obtener ciclistas: " + e.getMessage());
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener ciclistas: " + e.getMessage());
        }
        return ciclistas;
    }

    public List<String[]> obtenerEtapas() throws SQLException {
        List<String[]> etapas = new ArrayList<>();
        try (Statement stmt = conexion.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("""
                    SELECT * FROM etapas
                    """)) {
                while (rs.next()) {
                    etapas.add(new String[] { String.valueOf(rs.getInt("id_etapa")),
                            String.valueOf(rs.getInt("id_carrera")), String.valueOf(rs.getInt("num_etapa")),
                            rs.getString("fecha"), rs.getString("salida"), rs.getString("llegada"),
                            String.valueOf(rs.getDouble("distancia_km")), rs.getString("tipo") });
                }
            } catch (SQLException e) {
                throw new SQLException("Error al obtener etapas: " + e.getMessage());
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener etapas: " + e.getMessage());
        }
        return etapas;
    }

    public List<String[]> resumenPorTipoEtapa() throws SQLException {
        List<String[]> etapas = new ArrayList<>();
        try (Statement stmt = conexion.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("""
                    SELECT tipo,
                           COUNT(*) AS cantidad,
                           SUM(distancia_km) AS distancia_total
                    FROM etapas
                    GROUP BY tipo
                    """)) {
                while (rs.next()) {
                    etapas.add(new String[] { rs.getString("tipo"), String.valueOf(rs.getInt("cantidad")),
                            String.valueOf(rs.getDouble("distancia_total")) });
                }
            } catch (SQLException e) {
                throw new SQLException("Error al obtener etapas: " + e.getMessage());
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener etapas: " + e.getMessage());
        }
        return etapas;
    }

    public List<String[]> obtenerCiclistas() throws SQLException {
        List<String[]> ciclistas = new ArrayList<>();
        try (Statement stmt = conexion.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("""
                    SELECT * FROM ciclistas
                    """)) {
                while (rs.next()) {
                    ciclistas.add(new String[] { String.valueOf(rs.getInt("id_ciclista")),
                            rs.getString("nombre") });
                }
            } catch (SQLException e) {
                throw new SQLException("Error al obtener ciclistas: " + e.getMessage());
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener ciclistas: " + e.getMessage());
        }
        return ciclistas;
    }

    public String[] velocidadMediaPorCiclista(int id_ciclista) throws SQLException {
        String[] dato = new String[2];
        try (Statement stmt = conexion.createStatement()) {
            try (ResultSet rs = stmt.executeQuery(
                    """
                            SELECT ciclistas.id_ciclista,
                                   AVG(etapas.distancia_km / (EXTRACT(EPOCH FROM resultados_etapa.tiempo) / 3600)) AS velocidad_media
                            FROM resultados_etapa
                            JOIN ciclistas ON resultados_etapa.id_ciclista = ciclistas.id_ciclista
                            JOIN etapas ON resultados_etapa.id_etapa = etapas.id_etapa
                            WHERE ciclistas.id_ciclista = %d AND etapas.finalizada = true
                            GROUP BY ciclistas.id_ciclista
                            """
                            .formatted(id_ciclista))) {
                while (rs.next()) {
                    dato[0] = String.valueOf(rs.getInt("id_ciclista"));
                    dato[1] = String.valueOf(rs.getDouble("velocidad_media"));
                }
            } catch (SQLException e) {
                throw new SQLException("Error al obtener etapas: " + e.getMessage());
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener etapas: " + e.getMessage());
        }
        return dato;
    }

    public List<String[]> clasificacionPorEtapa(int id_etapa) throws SQLException {
        List<String[]> clasificacion = new ArrayList<>();
        try (Statement stmt = conexion.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("""
                    SELECT resultados_etapa.id_ciclista,
                           ciclistas.nombre AS nombre_ciclista,
                           equipos.nombre AS nombre_equipo,
                           resultados_etapa.tiempo
                    FROM resultados_etapa
                    JOIN ciclistas ON resultados_etapa.id_ciclista = ciclistas.id_ciclista
                    JOIN equipos ON ciclistas.id_equipo = equipos.id_equipo
                    WHERE resultados_etapa.id_etapa = %d
                    ORDER BY EXTRACT(EPOCH FROM resultados_etapa.tiempo) ASC
                    """.formatted(id_etapa))) {
                int position = 1;
                while (rs.next()) {
                    clasificacion.add(new String[] {
                            String.valueOf(position),
                            rs.getString("nombre_ciclista"),
                            rs.getString("nombre_equipo"),
                            rs.getString("tiempo")
                    });
                    position++;
                }
            } catch (SQLException e) {
                throw new SQLException("Error al obtener etapas: " + e.getMessage());
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener etapas: " + e.getMessage());
        }
        return clasificacion;
    }

    public List<String[]> clasificacionCiclistasEnMontaña() throws SQLException {
        List<String[]> clasificacion = new ArrayList<>();
        try (Statement stmt = conexion.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("""
                    SELECT resultados_puerto.id_ciclista,
                           ciclistas.nombre AS nombre_ciclista,
                           equipos.nombre AS nombre_equipo,
                           SUM(resultados_puerto.puntos) AS puntos
                    FROM resultados_puerto
                    JOIN ciclistas ON resultados_puerto.id_ciclista = ciclistas.id_ciclista
                    JOIN equipos ON ciclistas.id_equipo = equipos.id_equipo
                    GROUP BY resultados_puerto.id_ciclista, ciclistas.nombre, equipos.nombre
                    ORDER BY puntos DESC
                    """)) {
                int position = 1;
                while (rs.next()) {
                    clasificacion.add(new String[] {
                            String.valueOf(position),
                            rs.getString("nombre_ciclista"),
                            rs.getString("nombre_equipo"),
                            String.valueOf(rs.getInt("puntos"))
                    });
                    position++;
                }
            } catch (SQLException e) {
                throw new SQLException("Error al obtener etapas: " + e.getMessage());
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener etapas: " + e.getMessage());
        }
        return clasificacion;
    }

    public List<String[]> clasificacionRegularidad() throws SQLException {
        List<String[]> clasificacion = new ArrayList<>();
        try (Statement stmt = conexion.createStatement()) {
            try (ResultSet rs = stmt.executeQuery(
                    """
                            SELECT ciclistas.id_ciclista,
                                   ciclistas.nombre AS nombre_ciclista,
                                   equipos.nombre AS nombre_equipo,
                                   COALESCE(SUM(metas.puntos_meta), 0) + COALESCE(SUM(resultados_sprint.puntos), 0) AS puntos_totales
                            FROM ciclistas
                            JOIN equipos ON ciclistas.id_equipo = equipos.id_equipo
                            LEFT JOIN metas ON ciclistas.id_ciclista = metas.id_ciclista
                            LEFT JOIN resultados_sprint ON ciclistas.id_ciclista = resultados_sprint.id_ciclista
                            GROUP BY ciclistas.id_ciclista, ciclistas.nombre, equipos.nombre
                            ORDER BY puntos_totales DESC
                            """)) {
                int position = 1;
                while (rs.next()) {
                    clasificacion.add(new String[] {
                            String.valueOf(position),
                            rs.getString("nombre_ciclista"),
                            rs.getString("nombre_equipo"),
                            String.valueOf(rs.getInt("puntos_totales"))
                    });
                    position++;
                }
            } catch (SQLException e) {
                throw new SQLException("Error al obtener clasificación de regularidad: " + e.getMessage());
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener clasificación de regularidad: " + e.getMessage());
        }
        return clasificacion;
    }

    public List<String[]> clasificacionGeneral() throws SQLException {
        List<String[]> clasificacion = new ArrayList<>();
        try (Statement stmt = conexion.createStatement()) {
            try (ResultSet rs = stmt.executeQuery(
                    """
                            SELECT ciclistas.id_ciclista,
                                   ciclistas.nombre AS nombre_ciclista,
                                   equipos.nombre AS nombre_equipo,
                                   SUM(resultados_etapa.tiempo) AS tiempo_total
                            FROM resultados_etapa
                            JOIN ciclistas ON resultados_etapa.id_ciclista = ciclistas.id_ciclista
                            JOIN equipos ON ciclistas.id_equipo = equipos.id_equipo
                            GROUP BY ciclistas.id_ciclista, ciclistas.nombre, equipos.nombre
                            ORDER BY tiempo_total ASC
                            """)) {
                int position = 1;
                while (rs.next()) {
                    clasificacion.add(new String[] {
                            String.valueOf(position),
                            rs.getString("nombre_ciclista"),
                            rs.getString("nombre_equipo"),
                            rs.getString("tiempo_total")
                    });
                    position++;
                }
            } catch (SQLException e) {
                throw new SQLException("Error al obtener clasificación general: " + e.getMessage());
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener clasificación general: " + e.getMessage());
        }
        return clasificacion;
    }

    public List<String[]> clasificacionPorEquipos() throws SQLException {
        List<String[]> clasificacion = new ArrayList<>();
        try (Statement stmt = conexion.createStatement()) {
            try (ResultSet rs = stmt.executeQuery(
                    """
                            SELECT id_equipo,
                                   nombre AS nombre_equipo,
                                   SUM(tiempo_ciclista) AS tiempo_total
                            FROM (
                                SELECT equipos.id_equipo,
                                       equipos.nombre,
                                       etapas.id_etapa,
                                       resultados_etapa.tiempo AS tiempo_ciclista,
                                       ROW_NUMBER() OVER (PARTITION BY equipos.id_equipo, etapas.id_etapa ORDER BY resultados_etapa.tiempo ASC) AS rn
                                FROM resultados_etapa
                                JOIN ciclistas ON resultados_etapa.id_ciclista = ciclistas.id_ciclista
                                JOIN equipos ON ciclistas.id_equipo = equipos.id_equipo
                                JOIN etapas ON resultados_etapa.id_etapa = etapas.id_etapa
                            ) AS mejores_ciclistas
                            WHERE rn <= 3
                            GROUP BY id_equipo, nombre
                            ORDER BY tiempo_total ASC
                            """)) {
                int position = 1;
                while (rs.next()) {
                    clasificacion.add(new String[] {
                            String.valueOf(position),
                            rs.getString("nombre_equipo"),
                            rs.getString("tiempo_total")
                    });
                    position++;
                }
            } catch (SQLException e) {
                throw new SQLException("Error al obtener clasificación por equipos: " + e.getMessage());
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener clasificación por equipos: " + e.getMessage());
        }
        return clasificacion;
    }
}
