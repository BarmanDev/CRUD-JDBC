package org.barmandev.java.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBaseDatos {
    private static String url = "jdbc:mysql://localhost:3306/java_curso?serverTimezone=UTC";
    private static String username = "root"; // Nombre de usuario de la base de datos
    private static String password = "freya"; // Contraseña de la base de datos
    private static Connection connection; // -> Conexión singleton

    public static Connection getInstance() throws SQLException {
        if(connection == null){
            connection = DriverManager.getConnection(url, username,password);
        }
        return connection;
    }


}
