package org.example.alquiler_vehiculos;

/**
 * Clase que almacena el id del usuario
 * @author Rafael Haro
 */
public class SesionUsuario {
    /**
     * Crea una instancia
     */
    private static SesionUsuario instancia;
    /**
     * Almacena la id
     */
    private int userId;

    /**
     * Contructor vacio
     */
    private SesionUsuario() { }

    /**
     * Metodo par obtener la instacia
     * @return retorna la instancia
     */
    public static SesionUsuario getInstancia() {
        if (instancia == null) {
            instancia = new SesionUsuario();
        }
        return instancia;
    }

    /**
     * Setter para la id
     * @param userId Id del usuario
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Getter del usuario
     * @return la id el usuario
     */
    public int getUserId() {
        return userId;
    }

}
