package com.germangascon.ejemplosada.tema02.repository.jdbc;

import com.germangascon.ejemplosada.tema02.connection.DataSource;
import com.germangascon.ejemplosada.tema02.exception.DataAccessException;
import com.germangascon.ejemplosada.tema02.model.Producto;
import com.germangascon.ejemplosada.tema02.repository.dao.ProductoDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * <p><strong>ProductoDAOImplJDBC</strong></p>
 * <p>Descripción</p>
 * License: 🅮 Public Domain<br />
 * Created on: 2025-11-13<br />
 *
 * @author Germán Gascón <ggascon@gmail.com>
 * @version 0.0.1
 * @since 0.0.1
 **/
public class ProductoDAOImplJDBC implements ProductoDAO {
    private final DataSource dataSource;

    public ProductoDAOImplJDBC(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int insert(Producto producto) {
        String sql = """
                INSERT INTO producto (id, nombre, descripcion, precio, stock)
                              VALUES (?, ?, ?, ?, ?);
                """;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, producto.getId().toString());
            statement.setString(2, producto.getNombre());
            statement.setString(3, producto.getDescripcion());
            statement.setDouble(4, producto.getPrecio());
            statement.setInt(5, producto.getStock());
            return statement.executeUpdate();
        } catch (SQLException sqlException) {
            throw new DataAccessException("Error al insertar el producto " + producto, sqlException);
        }
    }

    @Override
    public int update(Producto producto) {
        String sql = """
                UPDATE producto SET nombre = ?, descripcion = ?, precio = ?, stock = ?
                WHERE id = ?;
                """;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, producto.getNombre());
            statement.setString(2, producto.getDescripcion());
            statement.setDouble(3, producto.getPrecio());
            statement.setInt(4, producto.getStock());
            statement.setString(5, producto.getId().toString());
            return statement.executeUpdate();
        } catch (SQLException sqlException) {
            throw new DataAccessException("Error al actualizar el producto " + producto, sqlException);
        }
    }

    @Override
    public int delete(UUID id) {
        String sql = "DELETE FROM producto WHERE id = ?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id.toString());
            return statement.executeUpdate();
        } catch (SQLException sqlException) {
            throw new DataAccessException("Error al borrar el producto con id " + id, sqlException);
        }
    }

    @Override
    public Optional<Producto> findById(UUID id) {
        String sql = """
                SELECT id, nombre, descripcion, precio, stock
                FROM producto
                WHERE id = ?;
                """;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id.toString());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(new Producto(
                            UUID.fromString(resultSet.getString("id")),
                            resultSet.getString("nombre"),
                            resultSet.getString("descripcion"),
                            resultSet.getDouble("precio"),
                            resultSet.getInt("stock")
                    ));
                }
            }
        } catch (SQLException sqlException) {
            throw new DataAccessException("Error al buscar el producto con id " + id, sqlException);
        }
        return Optional.empty();
    }

    @Override
    public List<Producto> findAll() {
        String sql = "SELECT id, nombre, descripcion, precio, stock FROM producto;";
        List<Producto> productos = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                productos.add(new Producto(
                        UUID.fromString(resultSet.getString("id")),
                        resultSet.getString("nombre"),
                        resultSet.getString("descripcion"),
                        resultSet.getDouble("precio"),
                        resultSet.getInt("stock")
                ));
            }
        } catch (SQLException sqlException) {
            throw new DataAccessException("Error al obtener el listado de productos", sqlException);
        }
        return productos;
    }
}
