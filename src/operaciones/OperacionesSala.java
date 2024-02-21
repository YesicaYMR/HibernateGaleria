/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operaciones;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import pojos.Autor;
import pojos.Sala;

/**
 *
 * @author Yesica
 */
public class OperacionesSala {

    /**
     * Obtiene la ID de una sala dado su nombre.
     *
     * @param nombreSala Nombre de la sala.
     * @return ID de la sala.
     */
    public Long obtenerIdSalaPorNombre(String nombreSala) {
        Configuration config = new Configuration();
        config.configure();
        SessionFactory sessionFactory = config.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Long idSala = null;
        try {
            transaction = session.beginTransaction();
            // Consulta para obtener la ID de la sala por su nombre
            idSala = (Long) session.createQuery("SELECT idSala FROM Sala WHERE nombre = :nombreSala")
                    .setParameter("nombreSala", nombreSala)
                    .uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return idSala;
    }

    /**
     * Obtiene todas las salas.
     *
     * @return Lista de salas.
     */
    public List<Sala> obtenerTodasSalas() {
        List<Sala> salas = null;
        Configuration config = new Configuration();
        config.configure();
        SessionFactory sessionFactory = config.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            Query query = session.createQuery("FROM Sala");
            salas = query.list();
        } catch (Exception e) {
            // Manejar cualquier excepción que pueda ocurrir durante la transacción
            e.printStackTrace();
        } finally {
            // Cerrar la sesión de Hibernate
            if (session != null) {
                session.close();
            }
        }
        return salas;
    }

    /**
     * Obtiene las salas que están libres en el momento actual.
     *
     * @return Lista de salas libres.
     */
    public List<Sala> obtenerSalasLibres() {
        Configuration config = new Configuration();
        config.configure();
        SessionFactory sessionFactory = config.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List<Sala> salas = null;
        try {
            String hql = "FROM Sala s WHERE s.idSala NOT IN (SELECT e.sala.idSala FROM Exposicion e WHERE e.fechBaja > current_date())";
            Query query = session.createQuery(hql);
            salas = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return salas;
    }

    /**
     * Obtiene el nombre de una sala dado su ID.
     *
     * @param idSala ID de la sala.
     * @return Nombre de la sala.
     */
    public String obtenerNombreSalaPorId(int idSala) {
        Configuration config = new Configuration();
        config.configure();
        SessionFactory sessionFactory = config.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        String nombre = null;
        try {
            transaction = session.beginTransaction();
            // Consulta para obtener la ID de la sala por su nombre
            nombre = (String) session.createQuery("SELECT nombre FROM Sala WHERE idSala = :idSala")
                    .setParameter("idSala", idSala)
                    .uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return nombre;
    }
}
