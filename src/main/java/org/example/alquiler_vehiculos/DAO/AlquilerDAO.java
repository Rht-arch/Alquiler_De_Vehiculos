package org.example.alquiler_vehiculos.DAO;

import org.example.alquiler_vehiculos.BD.AlquilerDetalle;
import org.example.alquiler_vehiculos.BD.ConexionBD;
import org.example.alquiler_vehiculos.BD.Alquileres;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clase que maneja las operaciones CRUD (Crear, Leer, Actualizar, Eliminar) para los alquileres.
 * @author Cristian Alejandro (Desarrollador principal)
 * @author Rafael Haro (Colaborador)
 * @author Alicia Pacheco (Colaborador)
 */
public class AlquilerDAO {

    /**
     * Inserta un nuevo alquiler en la base de datos.
     * Valida si el cliente y el vehículo existen antes de insertar el alquiler.
     * @param alquiler El objeto Alquileres a insertar.
     * @return true si la inserción fue exitosa, false en caso contrario.
     */
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



    /**
     * Obtiene un alquiler por su ID.
     * @param id El ID del alquiler a obtener.
     * @return El objeto Alquileres si se encuentra, null en caso contrario.
     */
    public Alquileres obtenerAlquilerPorId(int id) {
        String sql = "SELECT * FROM alquileresDetalles WHERE id = ?";
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

    /**
     * Obtiene todos los alquileres de la base de datos.
     * @return Una lista de objetos Alquileres.
     */
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

    /**
     * Actualiza un alquiler existente en la base de datos.
     * @param alquiler El objeto Alquileres con los datos actualizados.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
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

    /**
     * Elimina un alquiler por su ID de la base de datos.
     * @param id El ID del alquiler a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
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

    /**
     * Obtiene los alquileres de un cliente por su ID.
     * @param idCliente El ID del cliente cuyos alquileres se desean obtener.
     * @return Una lista de objetos AlquilerDetalle.
     */
    public List<AlquilerDetalle> obtenerAlquileresPorCliente(int idCliente) {
        List<AlquilerDetalle> alquileres = new ArrayList<>();
        String query = "SELECT * FROM alquileresDetalles WHERE id_cliente = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, idCliente);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                AlquilerDetalle alquiler = new AlquilerDetalle(
                        rs.getInt("id"),  // Asegurar que es "id" y no "idAlquiler"
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getInt("año"),
                        rs.getString("tipo"),
                        rs.getDate("fecha_inicio").toLocalDate(),
                        rs.getDate("fecha_fin").toLocalDate(),
                        rs.getDouble("total")
                );
                alquileres.add(alquiler);
            }
            System.out.println("🔍 Se encontraron " + alquileres.size() + " alquileres para el cliente ID: " + idCliente);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return alquileres;
    }


    /**
     * Obtiene el ID del vehículo basado en la marca y modelo.
     * @param marca La marca del vehículo.
     * @param modelo El modelo del vehículo.
     * @return El ID del vehículo si se encuentra, -1 si no existe.
     */
    public int obtenerIdVehiculo(String marca, String modelo) {
        String sql = "SELECT id FROM vehiculos WHERE marca = ? AND modelo = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, marca);
            pstmt.setString(2, modelo);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Retorna -1 si no se encuentra el vehículo
    }

    /**
     * Registra un nuevo alquiler en la tabla `alquileresDetalles`.
     * @param alquilerDetalle Objeto con los datos del alquiler.
     * @param idVehiculo ID del vehículo obtenido de la base de datos.
     * @return true si la inserción fue exitosa, false en caso contrario.
     */
    /**
     * Registra un nuevo alquiler en la tabla `alquileresDetalles`.
     * @param alquilerDetalle Objeto con los datos del alquiler.
     * @param idVehiculo ID del vehículo obtenido de la base de datos.
     * @param anioVehiculo Año del vehículo.
     * @return true si la inserción fue exitosa, false en caso contrario.
     */
    public int registrarAlquiler(AlquilerDetalle alquilerDetalle, int idVehiculo, int anioVehiculo) {
        int alquilerId = -1; // Inicializamos con un valor de error
        String sql = "INSERT INTO alquileresDetalles (id_cliente, id_vehiculo, marca, modelo, año, tipo, fecha_inicio, fecha_fin, total) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { // 🔹 Asegurar que se soliciten claves generadas

            pstmt.setInt(1, alquilerDetalle.getIdCliente());
            pstmt.setInt(2, idVehiculo);
            pstmt.setString(3, alquilerDetalle.getMarca());
            pstmt.setString(4, alquilerDetalle.getModelo());
            pstmt.setInt(5, anioVehiculo);
            pstmt.setString(6, alquilerDetalle.getTipo());
            pstmt.setDate(7, java.sql.Date.valueOf(alquilerDetalle.getFechaInicio()));
            pstmt.setDate(8, java.sql.Date.valueOf(alquilerDetalle.getFechaFin()));
            pstmt.setDouble(9, alquilerDetalle.getTotal());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) { // 🔹 Obtener la clave generada
                    if (generatedKeys.next()) {
                        alquilerId = generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alquilerId;
    }



    /**
     * Obtiene el año del vehículo basado en la marca y modelo.
     * @param marca La marca del vehículo.
     * @param modelo El modelo del vehículo.
     * @return El año del vehículo si se encuentra, -1 si no existe.
     */
    public int obtenerAnioVehiculo(String marca, String modelo) {
        String sql = "SELECT año FROM vehiculos WHERE marca = ? AND modelo = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, marca);
            pstmt.setString(2, modelo);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("año");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Retorna -1 si no se encuentra el vehículo
    }



}
