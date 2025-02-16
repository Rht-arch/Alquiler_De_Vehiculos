package org.example.alquiler_vehiculos.BD;

import java.util.Date;

/**
 * Clase que representa un alquiler de vehículos.
 * Contiene información como el ID del alquiler, ID del cliente, ID del vehículo,
 * fechas de inicio y fin del alquiler, y el total a pagar.
 *
 * @author Cristian Alejandro (Desarrollador principal)
 * @author Rafael Haro (Colaborador)
 * @author Alicia Pacheco (Colaborador)
 */
public class Alquileres {

    /**
     * ID del alquiler.
     */
    private int id;

    /**
     * ID del cliente que realiza el alquiler.
     */
    private int idCliente;

    /**
     * ID del vehículo alquilado.
     */
    private int idVehiculo;

    /**
     * Fecha de inicio del alquiler.
     */
    private Date fechaInicio;

    /**
     * Fecha de fin del alquiler.
     */
    private Date fechaFin;

    /**
     * Total a pagar por el alquiler.
     */
    private float total;

    /**
     * Constructor vacío de la clase Alquileres.
     */
    public Alquileres() {}

    /**
     * Constructor con parámetros de la clase Alquileres.
     * @param id ID del alquiler.
     * @param idCliente ID del cliente que realiza el alquiler.
     * @param idVehiculo ID del vehículo alquilado.
     * @param fechaInicio Fecha de inicio del alquiler.
     * @param fechaFin Fecha de fin del alquiler.
     * @param total Total a pagar por el alquiler.
     */
    public Alquileres(int id, int idCliente, int idVehiculo, Date fechaInicio, Date fechaFin, float total) {
        this.id = id;
        this.idCliente = idCliente;
        this.idVehiculo = idVehiculo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.total = total;
    }

    /**
     * Obtiene el ID del alquiler.
     * @return ID del alquiler.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el ID del alquiler.
     * @param id ID del alquiler.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el ID del cliente.
     * @return ID del cliente.
     */
    public int getIdCliente() {
        return idCliente;
    }

    /**
     * Establece el ID del cliente.
     * @param idCliente ID del cliente.
     */
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    /**
     * Obtiene el ID del vehículo.
     * @return ID del vehículo.
     */
    public int getIdVehiculo() {
        return idVehiculo;
    }

    /**
     * Establece el ID del vehículo.
     * @param idVehiculo ID del vehículo.
     */
    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    /**
     * Obtiene la fecha de inicio del alquiler.
     * @return Fecha de inicio del alquiler.
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Establece la fecha de inicio del alquiler.
     * @param fechaInicio Fecha de inicio del alquiler.
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Obtiene la fecha de fin del alquiler.
     * @return Fecha de fin del alquiler.
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * Establece la fecha de fin del alquiler.
     * @param fechaFin Fecha de fin del alquiler.
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * Obtiene el total a pagar por el alquiler.
     * @return Total a pagar por el alquiler.
     */
    public float getTotal() {
        return total;
    }

    /**
     * Establece el total a pagar por el alquiler.
     * @param total Total a pagar por el alquiler.
     */
    public void setTotal(float total) {
        this.total = total;
    }

    /**
     * Devuelve una representación en formato de cadena del objeto Alquileres.
     * @return Cadena que representa el objeto Alquileres.
     */
    @Override
    public String toString() {
        return "AlquilerDTO{" +
                "id=" + id +
                ", idCliente=" + idCliente +
                ", idVehiculo=" + idVehiculo +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", total=" + total +
                '}';
    }
}