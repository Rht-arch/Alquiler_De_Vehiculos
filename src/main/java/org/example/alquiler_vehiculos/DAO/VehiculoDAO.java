package org.example.alquiler_vehiculos.DAO;


import org.example.alquiler_vehiculos.BD.ConexionBD;
import org.example.alquiler_vehiculos.BD.Vehiculos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDAO {

    // Método para insertar un nuevo vehículo
    public boolean insertarVehiculo(Vehiculos vehiculo) {
        String sql = "INSERT INTO vehiculos (marca, modelo, año, tipo, precio_dia) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, vehiculo.getMarca());
            pstmt.setString(2, vehiculo.getModelo());
            pstmt.setInt(3, vehiculo.getAño());
            pstmt.setString(4, vehiculo.getTipo());
            pstmt.setDouble(5, vehiculo.getPreciodia());

            return pstmt.executeUpdate() > 0; // Devuelve true si la inserción fue exitosa
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para obtener un vehículo por su ID
    public Vehiculos obtenerVehiculoPorId(int id) {
        String sql = "SELECT * FROM vehiculos WHERE id = ?";
        Vehiculos vehiculo = null;

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                vehiculo = new Vehiculos(
                        rs.getInt("id"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getInt("año"),
                        rs.getString("tipo"),
                        rs.getDouble("precio_dia")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehiculo;
    }

    // Método para obtener todos los vehículos
    public List<Vehiculos> obtenerTodosLosVehiculos() {
        List<Vehiculos> listaVehiculos = new ArrayList<>();
        String sql = "SELECT * FROM vehiculos";

        try (Connection con = ConexionBD.getConexion();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Vehiculos vehiculo = new Vehiculos(
                        rs.getInt("id"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getInt("año"),
                        rs.getString("tipo"),
                        rs.getDouble("precio_dia")
                );
                listaVehiculos.add(vehiculo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaVehiculos;
    }

    // Método para actualizar un vehículo existente
    public boolean actualizarVehiculo(Vehiculos vehiculo) {
        String sql = "UPDATE vehiculos SET marca = ?, modelo = ?, año = ?, tipo = ?, precio_dia = ? WHERE id = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, vehiculo.getMarca());
            pstmt.setString(2, vehiculo.getModelo());
            pstmt.setInt(3, vehiculo.getAño());
            pstmt.setString(4, vehiculo.getTipo());
            pstmt.setDouble(5, vehiculo.getPreciodia());
            pstmt.setInt(6, vehiculo.getId());

            return pstmt.executeUpdate() > 0; // Devuelve true si la actualización fue exitosa
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para eliminar un vehículo por su ID
    public boolean eliminarVehiculo(int id) {
        String sql = "DELETE FROM vehiculos WHERE id = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0; // Devuelve true si la eliminación fue exitosa
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    //Método para comprobar si existe un vehículo
    public boolean existeVehiculo(int id) {
        String sql = "SELECT COUNT(*) FROM vehiculos WHERE id = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // Devuelve true si el vehículo existe
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Vehiculos> obtenerVehiculosConFiltro(String sql) {
        List<Vehiculos> vehiculos = new ArrayList<>();

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Vehiculos vehiculo = new Vehiculos();
                    vehiculo.setId(rs.getInt("id"));
                    vehiculo.setTipo(rs.getString("tipo"));
                    vehiculo.setMarca(rs.getString("marca"));
                    vehiculo.setModelo(rs.getString("modelo"));
                    vehiculo.setAño(rs.getInt("anio"));
                    vehiculo.setPreciodia(rs.getDouble("precio_dia"));
                    vehiculos.add(vehiculo);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de excepciones
        }

        return vehiculos;
    }



}
