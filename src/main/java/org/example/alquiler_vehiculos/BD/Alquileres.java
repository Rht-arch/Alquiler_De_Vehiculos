package org.example.alquiler_vehiculos.BD;

import java.util.Date;

public class Alquileres {

        private int id;
        private int idCliente;
        private int idVehiculo;
        private Date fechaInicio;
        private Date fechaFin;
        private float total;

        // Constructor vacío
        public Alquileres() {}

        // Constructor con parámetros
        public Alquileres(int id, int idCliente, int idVehiculo, Date fechaInicio, Date fechaFin, float total) {
            this.id = id;
            this.idCliente = idCliente;
            this.idVehiculo = idVehiculo;
            this.fechaInicio = fechaInicio;
            this.fechaFin = fechaFin;
            this.total = total;
        }

        // Getters y Setters
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIdCliente() {
            return idCliente;
        }

        public void setIdCliente(int idCliente) {
            this.idCliente = idCliente;
        }

        public int getIdVehiculo() {
            return idVehiculo;
        }

        public void setIdVehiculo(int idVehiculo) {
            this.idVehiculo = idVehiculo;
        }

        public Date getFechaInicio() {
            return fechaInicio;
        }

        public void setFechaInicio(Date fechaInicio) {
            this.fechaInicio = fechaInicio;
        }

        public Date getFechaFin() {
            return fechaFin;
        }

        public void setFechaFin(Date fechaFin) {
            this.fechaFin = fechaFin;
        }

        public float getTotal() {
            return total;
        }

        public void setTotal(float total) {
            this.total = total;
        }
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
