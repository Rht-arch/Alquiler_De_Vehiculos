package org.example.alquiler_vehiculos.BD;

/**
 * Clase que representa un vehículo disponible para alquiler.
 * Contiene información como el ID del vehículo, marca, modelo, año, tipo y precio por día.
 *
 * @author Cristian Alejandro (Desarrollador principal)
 * @author Rafael Haro (Colaborador)
 * @author Alicia Pacheco (Colaborador)
 */
public class Vehiculos {

    /**
     * ID del vehículo.
     */
    private int id;

    /**
     * Marca del vehículo.
     */
    private String marca;

    /**
     * Modelo del vehículo.
     */
    private String modelo;

    /**
     * Año del vehículo.
     */
    private int año;

    /**
     * Tipo de vehículo (por ejemplo, coche, moto, camión).
     */
    private String tipo;

    /**
     * Precio por día de alquiler del vehículo.
     */
    private double precio_dia;

    /**
     * Constructor vacío de la clase Vehiculos.
     */
    public Vehiculos() {}

    /**
     * Constructor con parámetros de la clase Vehiculos.
     * @param id ID del vehículo.
     * @param marca Marca del vehículo.
     * @param modelo Modelo del vehículo.
     * @param año Año del vehículo.
     * @param tipo Tipo de vehículo.
     * @param precio_dia Precio por día de alquiler del vehículo.
     */
    public Vehiculos(int id, String marca, String modelo, int año, String tipo, double precio_dia) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.año = año;
        this.tipo = tipo;
        this.precio_dia = precio_dia;
    }

    /**
     * Obtiene el ID del vehículo.
     * @return ID del vehículo.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el ID del vehículo.
     * @param id ID del vehículo.
     */
    public void setId(int id) {
        this.id = id;
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
     * Obtiene el precio por día de alquiler del vehículo.
     * @return Precio por día de alquiler del vehículo.
     */
    public double getPreciodia() {
        return precio_dia;
    }

    /**
     * Establece el precio por día de alquiler del vehículo.
     * @param precio_dia Precio por día de alquiler del vehículo.
     */
    public void setPreciodia(double precio_dia) {
        this.precio_dia = precio_dia;
    }

    /**
     * Devuelve una representación en formato de cadena del objeto Vehiculos.
     * @return Cadena que representa el objeto Vehiculos.
     */
    @Override
    public String toString() {
        return "Vehiculos{" +
                "id=" + id +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", año=" + año +
                ", tipo='" + tipo + '\'' +
                ", precioDia=" + precio_dia +
                '}';
    }
}