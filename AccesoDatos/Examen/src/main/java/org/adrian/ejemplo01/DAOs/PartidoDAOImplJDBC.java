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
import org.adrian.ejemplo01.model.Partido;

public class PartidoDAOImplJDBC implements PartidoDAO {
    private final DataSource dataSource;

    public PartidoDAOImplJDBC(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Partido> findAll() {
        String sql = "SELECT id, nombre, anyoFundacion FROM partido;";
        List<Partido> partidos = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            EquipoDAOImplJDBC equipoDAOImplJDBC = new EquipoDAOImplJDBC(dataSource);
            while (resultSet.next()) {
                partidos.add(new Partido(
                        resultSet.getInt("id"),
                        resultSet.getInt("numero_jornada"),
                        equipoDAOImplJDBC.findById(resultSet.getInt("id_equipo_local")).get(),
                        equipoDAOImplJDBC.findById(resultSet.getInt("id_equipo_visitante")).get(),
                        resultSet.getInt("goles_equipo_local"),
                        resultSet.getInt("goles_equipo_visitante")));
            }
        } catch (SQLException sqlException) {
            throw new DataAccessException("Error al obtener el listado de partidos", sqlException);
        }
        return partidos;
    }

    @Override
    public Optional<Partido> findById(int id) {
        String sql = """
                SELECT id, numero_jornada, id_equipo_local, id_equipo_visitante, goles_equipo_local, goles_equipo_visitante
                FROM partido
                WHERE id = ?;
                """;
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id + "");
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    EquipoDAOImplJDBC equipoDAOImplJDBC = new EquipoDAOImplJDBC(dataSource);
                    return Optional.of(new Partido(
                            resultSet.getInt("id"),
                            resultSet.getInt("numero_jornada"),
                            equipoDAOImplJDBC.findById(resultSet.getInt("id_equipo_local")).get(),
                            equipoDAOImplJDBC.findById(resultSet.getInt("id_equipo_visitante")).get(),
                            resultSet.getInt("goles_equipo_local"),
                            resultSet.getInt("goles_equipo_visitante")));
                }
            }
        } catch (SQLException sqlException) {
            throw new DataAccessException("Error al buscar el partido con id " + id, sqlException);
        }
        return Optional.empty();
    }

    @Override
    public List<Partido> findByJornada(int numeroJornada) {
        String sql = """
                SELECT id, numero_jornada, id_equipo_local, id_equipo_visitante, goles_equipo_local, goles_equipo_visitante
                FROM partido
                WHERE numero_jornada = ?;
                """;
        List<Partido> partidos = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, numeroJornada + "");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    EquipoDAOImplJDBC equipoDAOImplJDBC = new EquipoDAOImplJDBC(dataSource);
                    partidos.add(new Partido(
                            resultSet.getInt("id"),
                            resultSet.getInt("numero_jornada"),
                            equipoDAOImplJDBC.findById(resultSet.getInt("id_equipo_local")).get(),
                            equipoDAOImplJDBC.findById(resultSet.getInt("id_equipo_visitante")).get(),
                            resultSet.getInt("goles_equipo_local"),
                            resultSet.getInt("goles_equipo_visitante")));
                }
            }
        } catch (SQLException sqlException) {
            throw new DataAccessException("Error al obtener el listado de partidos", sqlException);
        }
        return partidos;
    }

}
