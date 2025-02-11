package org.example.alquiler_vehiculos.BD;

public class Vehiculos {
    private int id;
    private String marca;
    private String modelo;
    private int año;
    private String tipo;
    private double precioDia;

    // Constructor vacío
    public Vehiculos() {}

    // Constructor con parámetros
    public Vehiculos(int id, String marca, String modelo, int año, String tipo, double precioDia) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.año = año;
        this.tipo = tipo;
        this.precioDia = precioDia;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getPrecioDia() {
        return precioDia;
    }

    public void setPrecioDia(double precioDia) {
        this.precioDia = precioDia;
    }

    // Método toString para depuración
    @Override
    public String toString() {
        return "Vehiculos{" +
                "id=" + id +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", año=" + año +
                ", tipo='" + tipo + '\'' +
                ", precioDia=" + precioDia +
                '}';
    }
}
