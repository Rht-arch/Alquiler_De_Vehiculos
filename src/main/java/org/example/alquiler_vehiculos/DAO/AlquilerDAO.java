package org.example.alquiler_vehiculos.DAO;

import org.example.alquiler_vehiculos.BD.ConexionBD;
import org.example.alquiler_vehiculos.BD.Alquileres;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AlquilerDAO {

    // Método para insertar un nuevo alquiler
    public boolean insertarAlquiler(Alquileres alquiler) {
        // Validar si el cliente y el vehículo existen
        ClientesDAO clienteDAO = new ClientesDAO();
        VehiculoDAO vehiculoDAO = new VehiculoDAO();

        if (!clienteDAO.existeCliente(alquiler.getIdCliente())) {
            System.out.println("Error: El cliente con ID " + alquiler.getIdCliente() + " no existe.");
            return false;
        }

        if (!vehiculoDAO.existeVehiculo(alquiler.getIdVehiculo())) {
            System.out.println("Error: El vehículo con ID " + alquiler.getIdVehiculo() + " no existe.");
            return false;
        }

        String sql = "INSERT INTO alquileres (id_cliente, id_vehiculo, fecha_inicio, fecha_fin, total) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Convertir la fecha para que entre en la bd
            java.sql.Date sqlFechaInicio = new java.sql.Date(alquiler.getFechaInicio().getTime());
            java.sql.Date sqlFechaFin = new java.sql.Date(alquiler.getFechaFin().getTime());

            pstmt.setInt(1, alquiler.getIdCliente());
            pstmt.setInt(2, alquiler.getIdVehiculo());
            pstmt.setDate(3, sqlFechaInicio);
            pstmt.setDate(4, sqlFechaFin);
            pstmt.setFloat(5, alquiler.getTotal());

            return pstmt.executeUpdate() > 0; // Devuelve true si la inserción fue exitosa
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para obtener un alquiler por su ID
    public Alquileres obtenerAlquilerPorId(int id) {
        String sql = "SELECT * FROM alquileres WHERE id = ?";
        Alquileres alquiler = null;

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Convertir la fecha para recibir la fecha de la bd
                Date fechaInicio = new Date(rs.getDate("fecha_inicio").getTime());
                Date fechaFin = new Date(rs.getDate("fecha_fin").getTime());

                alquiler = new Alquileres(
                        rs.getInt("id"),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_vehiculo"),
                        fechaInicio,
                        fechaFin,
                        rs.getFloat("total")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alquiler;
    }

    // Método para obtener todos los alquileres
    public List<Alquileres> obtenerTodosLosAlquileres() {
        List<Alquileres> listaAlquileres = new ArrayList<>();
        String sql = "SELECT * FROM alquileres";

        try (Connection conn = ConexionBD.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // Convertir la fecha para recibir la fecha de la bd
                Date fechaInicio = new Date(rs.getDate("fecha_inicio").getTime());
                Date fechaFin = new Date(rs.getDate("fecha_fin").getTime());

                Alquileres alquiler = new Alquileres(
                        rs.getInt("id"),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_vehiculo"),
                        fechaInicio,
                        fechaFin,
                        rs.getFloat("total")
                );
                listaAlquileres.add(alquiler);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaAlquileres;
    }

    // Método para actualizar un alquiler existente
    public boolean actualizarAlquiler(Alquileres alquiler) {
        String sql = "UPDATE alquileres SET id_cliente = ?, id_vehiculo = ?, fecha_inicio = ?, fecha_fin = ?, total = ? WHERE id = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Convertir la fecha para que entre en la bd
            java.sql.Date sqlFechaInicio = new java.sql.Date(alquiler.getFechaInicio().getTime());
            java.sql.Date sqlFechaFin = new java.sql.Date(alquiler.getFechaFin().getTime());

            pstmt.setInt(1, alquiler.getIdCliente());
            pstmt.setInt(2, alquiler.getIdVehiculo());
            pstmt.setDate(3, sqlFechaInicio);
            pstmt.setDate(4, sqlFechaFin);
            pstmt.setFloat(5, alquiler.getTotal());
            pstmt.setInt(6, alquiler.getId());

            return pstmt.executeUpdate() > 0; // Devuelve true si la actualización fue exitosa
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para eliminar un alquiler por su ID
    public boolean eliminarAlquiler(int id) {
        String sql = "DELETE FROM alquileres WHERE id = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0; // Devuelve true si la eliminación fue exitosa
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
