package org.example.alquiler_vehiculos.DAO;

import org.example.alquiler_vehiculos.BD.Clientes;
import org.example.alquiler_vehiculos.BD.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que proporciona métodos para interactuar con la tabla de clientes en la base de datos.
 * Permite realizar operaciones como insertar, obtener, actualizar y eliminar clientes.
 * @author Cristian Alejandro (Desarrollador principal)
 * @author Rafael Haro (Colaborador)
 * @author Alicia Pacheco (Colaborador)
 */
public class ClientesDAO {

    /**
     * Inserta un nuevo cliente en la base de datos.
     * @param cliente El objeto Clientes que contiene los datos del cliente a insertar.
     * @return true si la inserción fue exitosa, false en caso contrario.
     */
    public boolean insertarCliente(Clientes cliente) {
        String sql = "INSERT INTO clientes (dni, nombre, apellido, telefono, correo, contraseña) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cliente.getDni());
            pstmt.setString(2, cliente.getNombre());
            pstmt.setString(3, cliente.getApellido());
            pstmt.setString(4, cliente.getTelefono());
            pstmt.setString(5, cliente.getCorreo());
            pstmt.setString(6, cliente.getContraseña());

            return pstmt.executeUpdate() > 0; // Devuelve true si la inserción fue exitosa
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtiene un cliente por su correo electrónico y contraseña.
     * @param correo El correo electrónico del cliente.
     * @param contra La contraseña del cliente.
     * @return Un objeto Clientes si se encuentra, o null si no existe.
     */
    public Clientes obtenerClientePorId(String correo, String contra) {
        String sql = "SELECT * FROM clientes WHERE correo = ? AND contraseña = ?";
        Clientes cliente = null;

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, correo);
            pstmt.setString(2, contra);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                cliente = new Clientes(
                        rs.getInt("id"),
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("telefono"),
                        rs.getString("correo"),
                        rs.getString("contraseña")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cliente; // Retorna null si el cliente no existe
    }

    /**
     * Obtiene todos los clientes registrados en la base de datos.
     * @return Una lista de objetos Clientes.
     */
    public List<Clientes> obtenerTodosLosClientes() {
        List<Clientes> listaClientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes";

        try (Connection conn = ConexionBD.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Clientes cliente = new Clientes(
                        rs.getInt("id"),
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("telefono"),
                        rs.getString("correo"),
                        rs.getString("contraseña")
                );
                listaClientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaClientes;
    }

    /**
     * Actualiza los datos de un cliente existente en la base de datos.
     * @param cliente El objeto Clientes con los datos actualizados.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    public boolean actualizarCliente(Clientes cliente) {
        String sql = "UPDATE clientes SET dni = ?, nombre = ?, apellido = ?, telefono = ?, correo = ?, contraseña = ? WHERE id = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cliente.getDni());
            pstmt.setString(2, cliente.getNombre());
            pstmt.setString(3, cliente.getApellido());
            pstmt.setString(4, cliente.getTelefono());
            pstmt.setString(5, cliente.getCorreo());
            pstmt.setString(6, cliente.getContraseña());
            pstmt.setInt(7, cliente.getId());

            return pstmt.executeUpdate() > 0; // Devuelve true si la actualización fue exitosa
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina un cliente de la base de datos por su ID.
     * @param id El ID del cliente a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    public boolean eliminarCliente(int id) {
        String sql = "DELETE FROM clientes WHERE id = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0; // Devuelve true si la eliminación fue exitosa
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Verifica si un cliente existe en la base de datos por su ID.
     * @param id El ID del cliente a verificar.
     * @return true si el cliente existe, false en caso contrario.
     */
    public boolean existeCliente(int id) {
        String sql = "SELECT COUNT(*) FROM clientes WHERE id = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // Devuelve true si el cliente existe
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public Integer obtenerIdPorCorreo(String correo, String contraseña) {
        Integer userId = null;
        String sql = "SELECT id FROM clientes WHERE correo = ? AND contraseña = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, correo);
            pstmt.setString(2, contraseña);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                userId = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userId;
    }

}