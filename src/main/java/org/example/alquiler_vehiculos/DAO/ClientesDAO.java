package org.example.alquiler_vehiculos.DAO;

import org.example.alquiler_vehiculos.BD.Clientes;
import org.example.alquiler_vehiculos.BD.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientesDAO {

    // Método para insertar un nuevo cliente
    public boolean insertarCliente(Clientes cliente) {
        String sql = "INSERT INTO clientes (nombre, apellido, telefono, correo, contrasenia) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cliente.getNombre());
            pstmt.setString(2, cliente.getApellido());
            pstmt.setString(3, cliente.getTelefono());
            pstmt.setString(4, cliente.getCorreo());
            pstmt.setString(5, cliente.getContrasenia());

            return pstmt.executeUpdate() > 0; // Devuelve true si se insertó al menos una fila
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    // Método para obtener un cliente por su ID
    public Clientes obtenerClientePorId(int id) {
        String sql = "SELECT * FROM clientes WHERE id = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Clientes(
                            rs.getString("contrasenia"),
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("telefono"),
                            rs.getString("correo")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Si no se encuentra el cliente
    }

    // Método para obtener todos los clientes
    public List<Clientes> obtenerTodosLosClientes() {
        List<Clientes> listaClientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes";

        try (Connection conn = ConexionBD.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                listaClientes.add(new Clientes(
                        rs.getString("contrasenia"),
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("telefono"),
                        rs.getString("correo")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaClientes;
    }

    // Método para actualizar un cliente existente
    public boolean actualizarCliente(Clientes cliente) {
        String sql = "UPDATE clientes SET nombre = ?, apellido = ?, telefono = ?, correo = ?, contrasenia = ? WHERE id = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cliente.getNombre());
            pstmt.setString(2, cliente.getApellido());
            pstmt.setString(3, cliente.getTelefono());
            pstmt.setString(4, cliente.getCorreo());
            pstmt.setString(5, cliente.getContrasenia());
            pstmt.setInt(6, cliente.getId());

            return pstmt.executeUpdate() > 0; // Devuelve true si se actualizó correctamente
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para eliminar un cliente por ID
    public boolean eliminarCliente(int id) {
        String sql = "DELETE FROM clientes WHERE id = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0; // Devuelve true si se eliminó correctamente
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
