package org.example.alquiler_vehiculos;

public class SesionUsuario {
    private static SesionUsuario instancia;
    private int userId;

    private SesionUsuario() { } // Constructor privado para Singleton

    public static SesionUsuario getInstancia() {
        if (instancia == null) {
            instancia = new SesionUsuario();
        }
        return instancia;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

}
