package org.barmandev.java.jdbc;

import org.barmandev.java.jdbc.modelo.Producto;
import org.barmandev.java.jdbc.repositorio.ProductoRepositorioImpl;
import org.barmandev.java.jdbc.repositorio.Repositorio;
import org.barmandev.java.jdbc.util.ConexionBaseDatos;

import java.sql.Connection;
import java.sql.SQLException;

public class MainPruebaDelete {
    public static void main(String[] args) {
        // Intenta establecer una conexión con la base de datos y automáticamente la cierra al salir del bloque try
        try (Connection conn = ConexionBaseDatos.getInstance()){
            // Crea una instancia de la implementación del repositorio de productos
            Repositorio<Producto> repositorio = new ProductoRepositorioImpl();
            System.out.println("-------------- LISTA --------------");
            repositorio.Listar().forEach(System.out::println);
            System.out.println("-----------------------------------");
            System.out.println("-------------- POR ID --------------");
            System.out.println(repositorio.porId((1L))); // imprime por id
            System.out.println("-----------------------------------");
            System.out.println("-------------- ELIMINAR PRODUCTO --------------");
            Producto producto = new Producto();
            // Modificamos el producto con id 3 para comprobar que funciona correctamente
            repositorio.eliminar(3L);
            System.out.println("El producto se ha eliminado con éxito");
            System.out.println("Su nueva lista de producto es: ");
            repositorio.Listar().forEach(System.out::println);



        } catch (SQLException e) {
            // Maneja la excepción SQLException e imprime la traza de la excepción
            e.printStackTrace();
        }
    }
}

