package org.example.alquiler_vehiculos.BD;

import java.time.LocalDate;

/**
 * Clase que representa los detalles de un alquiler de vehículos.
 * Contiene información como el ID del alquiler, marca, modelo, año, tipo de vehículo,
 * ID del cliente, fechas de inicio y fin del alquiler, y el total a pagar.
 *
 * @author Cristian Alejandro
 */
public class AlquilerDetalle {
    /**
     * ID del alquiler.
     */
    private int idAlquiler;

    /**
     * Marca del vehículo alquilado.
     */
    private String marca;

    /**
     * Modelo del vehículo alquilado.
     */
    private String modelo;

    /**
     * Año del vehículo alquilado.
     */
    private int año;

    /**
     * Tipo de vehículo alquilado (por ejemplo, coche, moto, camión).
     */
    private String tipo;

    /**
     * ID del cliente que realiza el alquiler.
     */
    private int idCliente;

    /**
     * Fecha de inicio del alquiler.
     */
    private LocalDate fechaInicio;

    /**
     * Fecha de fin del alquiler.
     */
    private LocalDate fechaFin;

    /**
     * Total a pagar por el alquiler.
     */
    private double total;

    /**
     * Constructor que inicializa un objeto AlquilerDetalle sin el ID del alquiler ni el año.
     * @param marca Marca del vehículo.
     * @param modelo Modelo del vehículo.
     * @param tipo Tipo de vehículo.
     * @param fechaInicio Fecha de inicio del alquiler.
     * @param fechaFin Fecha de fin del alquiler.
     * @param total Total a pagar por el alquiler.
     */
    public AlquilerDetalle(String marca, String modelo, String tipo,int idCliente, LocalDate fechaInicio, LocalDate fechaFin, double total) {
        this.marca = marca;
        this.modelo = modelo;
        this.tipo = tipo;
        this.idCliente = idCliente;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.total = total;
    }

    /**
     * Constructor que inicializa un objeto AlquilerDetalle con todos los atributos.
     * @param idAlquiler ID del alquiler.
     * @param marca Marca del vehículo.
     * @param modelo Modelo del vehículo.
     * @param año Año del vehículo.
     * @param tipo Tipo de vehículo.
     * @param fechaInicio Fecha de inicio del alquiler.
     * @param fechaFin Fecha de fin del alquiler.
     * @param total Total a pagar por el alquiler.
     */
    public AlquilerDetalle(int idAlquiler, String marca, String modelo, int año, String tipo, LocalDate fechaInicio, LocalDate fechaFin, double total) {
        this.idAlquiler = idAlquiler;
        this.marca = marca;
        this.modelo = modelo;
        this.año = año;
        this.tipo = tipo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.total = total;
    }

    /**
     * Obtiene el ID del alquiler.
     * @return ID del alquiler.
     */
    public int getIdAlquiler() {
        return idAlquiler;
    }

    /**
     * Establece el ID del alquiler.
     * @param idAlquiler ID del alquiler.
     */
    public void setIdAlquiler(int idAlquiler) {
        this.idAlquiler = idAlquiler;
    }

    /**
     * Obtiene la marca del vehículo.
     * @return Marca del vehículo.
     */
    public String getMarca() {
        return marca;
    }

    /**
     * Establece la marca del vehículo.
     * @param marca Marca del vehículo.
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * Obtiene el modelo del vehículo.
     * @return Modelo del vehículo.
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Establece el modelo del vehículo.
     * @param modelo Modelo del vehículo.
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * Obtiene el año del vehículo.
     * @return Año del vehículo.
     */
    public int getAño() {
        return año;
    }

    /**
     * Establece el año del vehículo.
     * @param año Año del vehículo.
     */
    public void setAño(int año) {
        this.año = año;
    }

    /**
     * Obtiene el tipo de vehículo.
     * @return Tipo de vehículo.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo de vehículo.
     * @param tipo Tipo de vehículo.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
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
     * Obtiene la fecha de inicio del alquiler.
     * @return Fecha de inicio del alquiler.
     */
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Establece la fecha de inicio del alquiler.
     * @param fechaInicio Fecha de inicio del alquiler.
     */
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Obtiene la fecha de fin del alquiler.
     * @return Fecha de fin del alquiler.
     */
    public LocalDate getFechaFin() {
        return fechaFin;
    }

    /**
     * Establece la fecha de fin del alquiler.
     * @param fechaFin Fecha de fin del alquiler.
     */
    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * Obtiene el total a pagar por el alquiler.
     * @return Total a pagar por el alquiler.
     */
    public double getTotal() {
        return total;
    }

    /**
     * Establece el total a pagar por el alquiler.
     * @param total Total a pagar por el alquiler.
     */
    public void setTotal(double total) {
        this.total = total;
    }
}