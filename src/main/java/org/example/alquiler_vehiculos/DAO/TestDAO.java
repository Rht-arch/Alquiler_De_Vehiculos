package org.example.alquiler_vehiculos.DAO;

import org.example.alquiler_vehiculos.BD.Vehiculos;

import java.util.List;

public class TestDAO {
    public static void main(String[] args) {
        VehiculoDAO vehiculoDAO = new VehiculoDAO();

        // 1️⃣ Insertar un nuevo vehículo
        Vehiculos nuevoVehiculo = new Vehiculos(0, "Toyota", "Corolla", 2022, "Sedán", 50.0);
        if (vehiculoDAO.insertarVehiculo(nuevoVehiculo)) {
            System.out.println("✅ Vehículo insertado correctamente.");
        }

        // 2️⃣ Obtener vehículo por ID
        Vehiculos vehiculo = vehiculoDAO.obtenerVehiculoPorId(1);
        if (vehiculo != null) {
            System.out.println("✅ Vehículo encontrado: " + vehiculo.getMarca() + " " + vehiculo.getModelo());
        }

        // 3️⃣ Obtener todos los vehículos
        List<Vehiculos> vehiculos = vehiculoDAO.obtenerTodosLosVehiculos();
        System.out.println("📋 Lista de vehículos:");
        for (Vehiculos v : vehiculos) {
            System.out.println(v);
        }

        // 4️⃣ Actualizar un vehículo
        if (vehiculo != null) {
            vehiculo.setPreciodia(55.0);
            if (vehiculoDAO.actualizarVehiculo(vehiculo)) {
                System.out.println("✅ Vehículo actualizado correctamente.");
            }
        }

        // 5️⃣ Eliminar un vehículo
        if (vehiculoDAO.eliminarVehiculo(1)) {
            System.out.println("✅ Vehículo eliminado correctamente.");
        }
    }
}
