package org.barmandev.java.jdbc.repositorio;

import org.barmandev.java.jdbc.modelo.Producto;
import org.barmandev.java.jdbc.util.ConexionBaseDatos;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepositorioImpl implements Repositorio<Producto> {

    private Connection getConnection() throws SQLException {
        return ConexionBaseDatos.getInstance();

    }
    @Override
    public List<Producto> Listar() {
        // Crear una lista para almacenar los objetos Producto
        List<Producto> productos = new ArrayList<>();

        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM productos")) {
            // Iterar sobre los resultados del conjunto de resultados
            while (rs.next()){
                // Crear un nuevo objeto Producto
                Producto p = crearProducto(rs);

                // Agregar el objeto Producto a la lista
                productos.add(p);
            }
        }catch (SQLException e) {
            // Manejar la excepción SQLException e imprimir la traza de la excepción
            e.printStackTrace();
        }

        // Devolver la lista de productos
        return productos;
    }

    @Override
    public Producto porId(Long id) {
        // Crea una variable Producto inicializada como null
        Producto producto = null;

        try (PreparedStatement stmt = getConnection().prepareStatement("SELECT * FROM productos WHERE id = ?")){
            // Establece el valor del parámetro de la sentencia SQL preparada
            stmt.setLong(1, id);

            // Utiliza un bloque try-with-resources para asegurarse de que el conjunto de resultados se cierre automáticamente al salir del bloque try
            try (ResultSet rs = stmt.executeQuery()) {
                // Verifica si hay un registro en el conjunto de resultados y crea un objeto Producto si lo hay
                if (rs.next()) {
                    producto = crearProducto(rs);
                }
            }
        } catch (SQLException e) {
            // Maneja la excepción SQLException e imprime la traza de la excepción
            e.printStackTrace();
        }

        // Devuelve el objeto Producto obtenido de la base de datos, o null si no se encontró ningún producto con el ID especificado
        return producto;
    }

    @Override
    public void guardar(Producto producto) {
        String sql;

        // Verifica si el producto tiene un ID asignado para determinar si se debe realizar una actualización o una inserción en la base de datos
        if (producto.getId() != null && producto.getId()>0) {
            sql = "UPDATE productos SET nombre=?, precio=? WHERE id=?";
        } else {
            sql = "INSERT INTO productos (nombre,precio,fecha_registro) VALUES(?,?,?)";
        }

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)){
            // Asigna los valores de los parámetros de la sentencia SQL preparada
            stmt.setString(1, producto.getNombre());
            stmt.setLong(2, producto.getPrecio());

            // Verifica nuevamente si el producto tiene un ID asignado para determinar el valor del parámetro adicional en la sentencia SQL
            if (producto.getId() != null && producto.getId()>0) {
                stmt.setLong(3, producto.getId());
            } else {
                stmt.setDate(3, new Date(producto.getFechaRegistro().getTime()));
            }

            // Ejecuta la sentencia SQL
            stmt.executeUpdate();
        }catch (SQLException e){
            // Maneja la excepción SQLException e imprime la traza de la excepción
            e.printStackTrace();
        }
    }
    @Override
    public void eliminar(Long id) {
        try (PreparedStatement stmt = getConnection().prepareStatement("DELETE FROM productos WHERE id=?")) {
            // Establece el valor del parámetro de la sentencia SQL preparada
            stmt.setLong(1, id);
            // Ejecuta la sentencia SQL para eliminar el producto de la base de datos
            stmt.executeUpdate();
        } catch (SQLException e) {
            // Maneja la excepción SQLException e imprime la traza de la excepción
            e.printStackTrace();
        }
    }

    private  Producto crearProducto(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        // Asignar los valores de los atributos del objeto Producto
        p.setId(rs.getLong("id"));
        p.setNombre(rs.getString("nombre"));
        p.setPrecio(rs.getInt("precio"));
        p.setFechaRegistro(rs.getDate("fecha_registro"));
        return p;
    }
}
