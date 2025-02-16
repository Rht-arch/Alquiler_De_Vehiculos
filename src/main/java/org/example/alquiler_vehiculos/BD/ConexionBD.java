package org.example.alquiler_vehiculos.BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase que maneja la conexión a la base de datos.
 * Proporciona métodos para obtener una conexión a la base de datos de alquiler de vehículos.
 * @author Cristian Alejandro (Desarrollador principal)
 * @author Rafael Haro (Colaborador)
 * @author Alicia Pacheco (Colaborador)
 */
public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/alquiler_vehiculos_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    /**
     * Obtiene una conexión a la base de datos.
     * @return Una conexión a la base de datos, o null si no se pudo establecer la conexión.
     */
    public static Connection getConexion() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos");
            e.printStackTrace();
            return null;
        }
    }
}
