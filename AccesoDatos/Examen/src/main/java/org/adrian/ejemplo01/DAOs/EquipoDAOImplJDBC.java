
package org.adrian.ejemplo01.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.adrian.ejemplo01.connection.DataSource;

import org.adrian.ejemplo01.exception.DataAccessException;
import org.adrian.ejemplo01.model.Equipo;

public class EquipoDAOImplJDBC implements EquipoDAO {
    private final DataSource dataSource;

    public EquipoDAOImplJDBC(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Equipo> findById(int id) {
        String sql = """
                SELECT id, nombre, anyo_fundacion
                FROM equipo
                WHERE id = ?;
                """;
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(new Equipo(
                            resultSet.getInt("id"),
                            resultSet.getString("nombre"),
                            resultSet.getInt("anyo_fundacion")));
                }
            }
        } catch (SQLException sqlException) {
            throw new DataAccessException("Error al buscar el equipo con id " + id, sqlException);
        }
        return Optional.empty();
    }

    @Override
    public List<Equipo> findAll() {
        String sql = "SELECT id, nombre, anyo_fundacion FROM equipo;";
        List<Equipo> equipos = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                equipos.add(new Equipo(
                        resultSet.getInt("id"),
                        resultSet.getString("nombre"),
                        resultSet.getInt("anyo_fundacion")));
            }
        } catch (SQLException sqlException) {
            throw new DataAccessException("Error al obtener el listado de equipos", sqlException);
        }
        return equipos;
    }

    @Override
    public List<Equipo> findByNombre(String nombre) {
        String sql = """
                SELECT id, nombre, anyoFundacion
                FROM equipo
                WHERE nombre = ?;
                """;
        List<Equipo> equipos = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            statement.setString(1, nombre + "");
            while (resultSet.next()) {
                equipos.add(new Equipo(
                        resultSet.getInt("id"),
                        resultSet.getString("nombre"),
                        resultSet.getInt("anyoFundacion")));
            }
        } catch (SQLException sqlException) {
            throw new DataAccessException("Error al obtener el listado de equipos", sqlException);
        }
        return equipos;
    }

    public void insertarEquipos() {
        String sql = """
                                CREATE TABLE equipo (
                    id INTEGER PRIMARY KEY,
                    nombre VARCHAR(100) NOT NULL,
                    anyo_fundacion INTEGER NOT NULL
                );

                INSERT INTO equipo (id, nombre, anyo_fundacion) VALUES (1, 'Athletic Club', 1898);
                INSERT INTO equipo (id, nombre, anyo_fundacion) VALUES (2, 'Atlético de Madrid', 1903);
                INSERT INTO equipo (id, nombre, anyo_fundacion) VALUES (3, 'CA Osasuna', 1920);
                INSERT INTO equipo (id, nombre, anyo_fundacion) VALUES (4, 'Celta', 1923);
                INSERT INTO equipo (id, nombre, anyo_fundacion) VALUES (5, 'Deportivo Alavés', 1921);
                INSERT INTO equipo (id, nombre, anyo_fundacion) VALUES (6, 'Elche CF', 1923);
                INSERT INTO equipo (id, nombre, anyo_fundacion) VALUES (7, 'FC Barcelona', 1899);
                INSERT INTO equipo (id, nombre, anyo_fundacion) VALUES (8, 'Getafe CF', 1983);
                INSERT INTO equipo (id, nombre, anyo_fundacion) VALUES (9, 'Girona FC', 1930);
                INSERT INTO equipo (id, nombre, anyo_fundacion) VALUES (10, 'Levante UD', 1909);
                INSERT INTO equipo (id, nombre, anyo_fundacion) VALUES (11, 'Rayo Vallecano', 1924);
                INSERT INTO equipo (id, nombre, anyo_fundacion) VALUES (12, 'RCD Espanyol', 1900);
                INSERT INTO equipo (id, nombre, anyo_fundacion) VALUES (13, 'RCD Mallorca', 1916);
                INSERT INTO equipo (id, nombre, anyo_fundacion) VALUES (14, 'Real Betis', 1907);
                INSERT INTO equipo (id, nombre, anyo_fundacion) VALUES (15, 'Real Madrid', 1902);
                INSERT INTO equipo (id, nombre, anyo_fundacion) VALUES (16, 'Real Oviedo', 1926);
                INSERT INTO equipo (id, nombre, anyo_fundacion) VALUES (17, 'Real Sociedad', 1909);
                INSERT INTO equipo (id, nombre, anyo_fundacion) VALUES (18, 'Sevilla FC', 1890);
                INSERT INTO equipo (id, nombre, anyo_fundacion) VALUES (19, 'Valencia CF', 1919);
                INSERT INTO equipo (id, nombre, anyo_fundacion) VALUES (20, 'Villarreal CF', 1923);
                                """;
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {

            }
        } catch (SQLException sqlException) {
            throw new DataAccessException("Error al obtener el listado de equipos", sqlException);
        }
    }
}
