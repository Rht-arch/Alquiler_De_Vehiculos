package org.example.alquiler_vehiculos.DAO;


import org.example.alquiler_vehiculos.BD.ConexionBD;
import org.example.alquiler_vehiculos.BD.Vehiculos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que proporciona métodos para interactuar con la tabla de vehiculos en la base de datos.
 * Permite realizar operaciones como insertar, obtener, actualizar y eliminar clientes.
 * @author Cristian Alejandro (Desarrollador principal)
 * @author Rafael Haro (Colaborador)
 * @author Alicia Pacheco (Colaborador)
 */
public class VehiculoDAO {

    /**
     * Inserta un nuevo vehiculo en la base de datos.
     * @param vehiculo El objeto Clientes que contiene los datos del cliente a insertar.
     * @return true si la inserción fue exitosa, false en caso contrario.
     */
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

    /**
     * Obtiene un Vehiculo por su id
     * @param id El id del cliente.
     * @return Un objeto Vehiculo si se encuentra, o null si no existe.
     */
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

    /**
     * Obtiene todos los vehiculos registrados en la base de datos.
     * @return Una lista de objetos Vehiculos.
     */
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

    /**
     * Metodo que actualiza el vehiculo
     * @param vehiculo Varibael vehiculo
     * @return devuelve la actualizacion
     */
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

    /**
     * Metodo que elima el Vehculo
     * @param id Variabel vehiculo
     * @return Devuelve el vehiculo eliminado
     */
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

    /**
     * Metodo que comprueba si existe vehiculos
     * @param id Variable id
     * @return Devurlve si o no
     */
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

    /**
     * Metodo que crea una lista con los parametros elegidos
     * @param sql conexion bd
     * @return devurlv evehiculos filtrado
     */
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
                    vehiculo.setAño(rs.getInt("año"));
                    vehiculo.setPreciodia(rs.getDouble("precio_dia"));
                    vehiculos.add(vehiculo);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de excepciones
        }

        return vehiculos;
    }

    /**
     * Metodo que agrupa en uan lista los Vehiculos
     * @return devuelve la lista
     */
    public List<Vehiculos> cargarVehiculos() {
        List<Vehiculos> vehiculos = new ArrayList<>();
        String query = "SELECT * FROM vehiculos";

        try (Connection con= ConexionBD.getConexion();
                Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Vehiculos vehiculo = new Vehiculos(
                        rs.getInt("id"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getInt("año"),
                        rs.getString("tipo"),
                        rs.getFloat("precio_dia")
                );
                vehiculos.add(vehiculo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vehiculos;
    }

    /**
     * Metodo que filtra los vehiculos
     * @param id Variable id
     * @param marca Variable marca
     * @param modelo Variable modelo
     * @param anio Variable anio
     * @param tipo Variable tipo
     * @param precio Variable precio
     * @return Devuelve los vehiculos
     */
    public List<Vehiculos> filtrarVehiculos(String id, String marca, String modelo, String anio, String tipo, String precio) {
        List<Vehiculos> vehiculos = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM vehiculos WHERE 1=1");

        // Añadir condiciones según los parámetros proporcionados
        if (id != null && !id.isEmpty()) {
            query.append(" AND id = ").append(Integer.parseInt(id));
        }
        if (marca != null && !marca.isEmpty()) {
            query.append(" AND marca LIKE '%").append(marca).append("%'");
        }
        if (modelo != null && !modelo.isEmpty()) {
            query.append(" AND modelo LIKE '%").append(modelo).append("%'");
        }
        if (anio != null && !anio.isEmpty()) {
            query.append(" AND año = ").append(Integer.parseInt(anio));
        }
        if (tipo != null && !tipo.isEmpty()) {
            query.append(" AND tipo LIKE '%").append(tipo).append("%'");
        }
        if (precio != null && !precio.isEmpty()) {
            query.append(" AND precio_dia = ").append(Float.parseFloat(precio));
        }

        try ( Connection con= ConexionBD.getConexion();
                Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query.toString())) {

            while (rs.next()) {
                Vehiculos vehiculo = new Vehiculos(
                        rs.getInt("id"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getInt("año"),
                        rs.getString("tipo"),
                        rs.getFloat("precio_dia")
                );
                vehiculos.add(vehiculo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vehiculos;
    }

}
