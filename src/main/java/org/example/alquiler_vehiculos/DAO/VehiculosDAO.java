package org.example.alquiler_vehiculos.DAO;

import org.example.alquiler_vehiculos.BD.ConexionBD;
import org.example.alquiler_vehiculos.BD.Vehiculos;

public class VehiculosDAO {
    public void InsertarVehiculos(Vehiculos vehiculo) {
        String sql = "Insert into vehiculos (String marca, String modelo, int año, String tipo, float precio_dia) values(?,?,?,?)";
    }
}
