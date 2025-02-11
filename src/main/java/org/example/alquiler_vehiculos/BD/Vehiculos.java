package org.example.alquiler_vehiculos.BD;

import jakarta.persistence.*;

@Entity
@Table(name = "vehiculos")
public class Vehiculos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "marca", nullable = false, length = 100)
    private String marca;

    @Column(name = "modelo", nullable = false, length = 100)
    private String modelo;

    @Column(name = "`año`", nullable = false)
    private Integer año;

    @Column(name = "tipo", nullable = false, length = 50)
    private String tipo;

    @Column(name = "precio_dia", nullable = false)
    private Float precioDia;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getAño() {
        return año;
    }

    public void setAño(Integer año) {
        this.año = año;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Float getPrecioDia() {
        return precioDia;
    }

    public void setPrecioDia(Float precioDia) {
        this.precioDia = precioDia;
    }

}