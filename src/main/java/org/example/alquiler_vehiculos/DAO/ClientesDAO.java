package org.example.alquiler_vehiculos.DAO;

import jakarta.persistence.*;
import org.example.alquiler_vehiculos.BD.Clientes;
import java.util.List;

public class ClientesDAO {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");

    // Método para insertar un nuevo cliente
    public void insertarCliente(Clientes cliente) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Método para obtener un cliente por su ID
    public Clientes obtenerClientePorId(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Clientes.class, id);
        } finally {
            em.close();
        }
    }

    // Método para obtener todos los clientes
    public List<Clientes> obtenerTodosLosClientes() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT c FROM Clientes c", Clientes.class).getResultList();
        } finally {
            em.close();
        }
    }

    // Método para actualizar un cliente existente
    public void actualizarCliente(Clientes cliente) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(cliente);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Método para eliminar un cliente por su ID
    public void eliminarCliente(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            Clientes cliente = em.find(Clientes.class, id);
            if (cliente != null) {
                em.getTransaction().begin();
                em.remove(cliente);
                em.getTransaction().commit();
            }
        } finally {
            em.close();
        }
    }

    // Método para cerrar el EntityManagerFactory cuando la aplicación termine
    public static void cerrarEntityManagerFactory() {
        if (emf.isOpen()) {
            emf.close();
        }
    }
}
