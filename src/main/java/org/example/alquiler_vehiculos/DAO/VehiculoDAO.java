package org.example.alquiler_vehiculos.DAO;

import jakarta.persistence.*;
import org.example.alquiler_vehiculos.BD.Vehiculos;

import java.util.List;

public class VehiculoDAO {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");

    // Método para insertar un nuevo vehículo
    public void insertarVehiculo(Vehiculos vehiculo) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(vehiculo);
        em.getTransaction().commit();
        em.close();
    }

    // Método para obtener un vehículo por su ID
    public Vehiculos obtenerVehiculoPorId(int id) {
        EntityManager em = emf.createEntityManager();
        Vehiculos vehiculo = em.find(Vehiculos.class, id);
        em.close();
        return vehiculo;
    }

    // Método para obtener todos los vehículos
    public List<Vehiculos> obtenerTodosLosVehiculos() {
        EntityManager em = emf.createEntityManager();
        List<Vehiculos> vehiculos = em.createQuery("SELECT v FROM Vehiculos v", Vehiculos.class).getResultList();
        em.close();
        return vehiculos;
    }

    // Método para actualizar un vehículo existente
    public void actualizarVehiculo(Vehiculos vehiculo) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(vehiculo);
        em.getTransaction().commit();
        em.close();
    }

    // Método para eliminar un vehículo por su ID
    public void eliminarVehiculo(int id) {
        EntityManager em = emf.createEntityManager();
        Vehiculos vehiculo = em.find(Vehiculos.class, id);
        if (vehiculo != null) {
            em.getTransaction().begin();
            em.remove(vehiculo);
            em.getTransaction().commit();
        }
        em.close();
    }
}
