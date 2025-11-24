package org.adrian.ejemplo01.DAOs;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.adrian.ejemplo01.model.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.adrian.ejemplo01.Exception.DataAccesException;

public class ProductoDAOImp implements ProductoDAO {

    private DataSource dataSource;

    public ProductoDAOImp(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int Insert(Producto producto) {
        String sql = "INSERT INTO productos (id, nombre, descripcion, precio, stock) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, producto.getId());
            ps.setString(2, producto.getNombre());
            ps.setString(3, producto.getDescripcion());
            ps.setDouble(4, producto.getPrecio());
            ps.setInt(5, producto.getStock());
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccesException("Error al insertar producto: " + producto + ": " + e.getMessage());
        }
    }

    @Override
    public int Update(Producto producto) {
        String sql = "UPDATE productos SET nombre = ?, descripcion = ?, precio = ?, stock = ? WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setDouble(3, producto.getPrecio());
            ps.setInt(4, producto.getStock());
            ps.setString(5, producto.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccesException("Error al actualizar producto: " + producto + ": " + e.getMessage());
        }
    }

    @Override
    public int Delete(String id) {
        String sql = "DELETE FROM productos WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccesException("Error al eliminar producto con id: " + id + ": " + e.getMessage());
        }
    }

    @Override
    public Optional<Producto> findById(String id) {
        String sql = "SELECT id, nombre, descripcion, precio, stock FROM productos WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            var rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(new Producto(
                        rs.getString("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio"),
                        rs.getInt("stock")));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataAccesException("Error al buscar producto con id: " + id + ": " + e.getMessage());
        }
    }

    @Override
    public List<Producto> findAll() {
        String sql = "SELECT id, nombre, descripcion, precio, stock FROM productos";
        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            var rs = ps.executeQuery();
            List<Producto> productos = new ArrayList<>();
            while (rs.next()) {
                productos.add(new Producto(
                        rs.getString("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio"),
                        rs.getInt("stock")));
            }
            return productos;
        } catch (SQLException e) {
            throw new DataAccesException("Error al buscar todos los productos: " + e.getMessage());
        }
    }
}
