/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.museo.DAO;

import app.museo.entities.Museos;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 *
 * @author crist
 */
public class MuseoPersistence {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public MuseoPersistence() {
        try {
            this.entityManagerFactory = Persistence.createEntityManagerFactory("museo_persistence");
            this.entityManager = entityManagerFactory.createEntityManager();
        } catch (Exception exception) {
            System.out.println("Error al cargar la unidad de persistencia");
            exception.printStackTrace();
        }
    }

    public void addMuseo(Museos museo) {
        entityManager.getTransaction().begin();
        entityManager.persist(museo);
        entityManager.getTransaction().commit();
    }
}
