package org.example.alquiler_vehiculos.BD;

import java.util.Date;

public class AlquilerDetalle {
    private String marca;
    private String modelo;
    private String tipo;
    private int idCliente;
    private Date fechaInicio;
    private Date fechaFin;
    private float total;

    public AlquilerDetalle(String marca, String modelo, String tipo, int idCliente, Date fechaInicio, Date fechaFin, float total) {
        this.marca = marca;
        this.modelo = modelo;
        this.tipo = tipo;
        this.idCliente = idCliente;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.total = total;
    }

    // Getters y Setters
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }

    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }

    public float getTotal() { return total; }
    public void setTotal(float total) { this.total = total; }
}
