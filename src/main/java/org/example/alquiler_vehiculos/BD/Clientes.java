package org.example.alquiler_vehiculos.BD;

/**
 * Clase que representa a un cliente.
 * Contiene información sobre el cliente como su ID, DNI, nombre, apellido, teléfono, correo y contraseña.
 * @author Cristian Alejandro (Desarrollador principal)
 * @author Rafael Haro (Colaborador)
 * @author Alicia Pacheco (Colaborador)
 */
public class Clientes {
    /**
     * Almacena la id del cliente
     */
    private int id;
    /**
     * Almacena el dni del cliente
     */
    private String dni;
    /**
     * Almacena el nombre del cliente
     */
    private String nombre;
    /**
     * Almacena el apellido del cliente
     */
    private String apellido;
    /**
     * Almacena el telefono del cliente
     */
    private String telefono;
    /**
     * Almacena el correo del cliente
     */
    private String correo;
    /**
     * Almacena la contraseña del cliente
     */
    private String contraseña;

    /**
     * Constructor vacío de la clase Clientes.
     */
    public Clientes() {}

    /**
     * Constructor con parámetros de la clase Clientes.
     * @param id El ID del cliente.
     * @param dni El DNI del cliente.
     * @param nombre El nombre del cliente.
     * @param apellido El apellido del cliente.
     * @param telefono El teléfono del cliente.
     * @param correo El correo electrónico del cliente.
     * @param contraseña La contraseña del cliente.
     */
    public Clientes(int id, String dni, String nombre, String apellido, String telefono, String correo, String contraseña) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.correo = correo;
        this.contraseña = contraseña;
    }

    /**
     * Obtiene el ID del cliente.
     * @return El ID del cliente.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el ID del cliente.
     * @param id El ID del cliente.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el DNI del cliente.
     * @return El DNI del cliente.
     */
    public String getDni() {
        return dni;
    }

    /**
     * Establece el DNI del cliente.
     * @param dni El DNI del cliente.
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Obtiene el nombre del cliente.
     * @return El nombre del cliente.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del cliente.
     * @param nombre El nombre del cliente.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el apellido del cliente.
     * @return El apellido del cliente.
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Establece el apellido del cliente.
     * @param apellido El apellido del cliente.
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * Obtiene el teléfono del cliente.
     * @return El teléfono del cliente.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el teléfono del cliente.
     * @param telefono El teléfono del cliente.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene el correo electrónico del cliente.
     * @return El correo electrónico del cliente.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece el correo electrónico del cliente.
     * @param correo El correo electrónico del cliente.
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Obtiene la contraseña del cliente.
     * @return La contraseña del cliente.
     */
    public String getContraseña() {
        return contraseña;
    }

    /**
     * Establece la contraseña del cliente.
     * @param contraseña La contraseña del cliente.
     */
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    /**
     * Devuelve una representación en forma de cadena del objeto Clientes.
     * @return Una cadena que contiene los detalles del cliente.
     */
    @Override
    public String toString() {
        return "ClienteDTO{" +
                "id=" + id +
                ", dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", telefono='" + telefono + '\'' +
                ", correo='" + correo + '\'' +
                ", contraseña='" + contraseña + '\'' +
                '}';
    }
}
