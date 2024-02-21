/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operaciones;

import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import pojos.Exposicion;
import pojos.ExposicionId;
import pojos.Obra;
import pojos.Sala;

/**
 *
 * @author Yesica
 */
public class OperacionesExposiciones {

    /**
     * Crea una nueva exposición y la guarda en la base de datos.
     *
     * @param id Identificador de la exposición.
     * @param nombre Nombre de la exposición.
     * @param nombreSala Nombre de la sala donde se realiza la exposición.
     * @param fechaAlta Fecha de inicio de la exposición.
     * @param fechaBaja Fecha de fin de la exposición.
     * @param obra Obra asociada a la exposición.
     * @return Verdadero si la exposición se creó correctamente, falso de lo
     * contrario.
     */
    public boolean crearExposicion(ExposicionId id, String nombre, String nombreSala, Date fechaAlta, Date fechaBaja, Obra obra) {
        boolean exito = false;
        // Obtener una sesión de Hibernate
        Configuration config = new Configuration();
        config.configure();
        SessionFactory sessionFactory = config.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            // Buscar la sala por su nombre
            Sala sala = buscarSalaPorNombre(session, nombreSala);
            // Crear la exposición
            Exposicion exposicion = new Exposicion();
            exposicion.setId(id);
            exposicion.setNombre(nombre);
            exposicion.setSala(sala);
            exposicion.setFechAlta(fechaAlta);
            exposicion.setFechBaja(fechaBaja);
            exposicion.setObra(obra);
            // Guardar la exposición en la base de datos
            session.save(exposicion);
            // Confirmar la transacción
            transaction.commit();
            exito = true;
        } catch (Exception e) {
            // Manejar cualquier excepción que pueda ocurrir durante la transacción
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            // Cerrar la sesión de Hibernate
            if (session != null) {
                session.close();
            }
        }
        return exito;
    }

    /**
     * Busca una sala por su nombre.
     *
     * @param session Sesión de Hibernate.
     * @param nombreSala Nombre de la sala a buscar.
     * @return Sala encontrada.
     */
    private Sala buscarSalaPorNombre(Session session, String nombreSala) {
        return (Sala) session.createQuery("FROM Sala WHERE nombre = :nombreSala")
                .setParameter("nombreSala", nombreSala)
                .uniqueResult();
    }

    /**
     * Obtiene el valor máximo de id_exposicion.
     *
     * @return Valor máximo de id_exposicion.
     */
    public int obtenerMaximoIdExposicion() {
        int maxIdExposicion = 0;
        Configuration config = new Configuration();
        config.configure();
        SessionFactory sessionFactory = config.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            // Consulta HQL para obtener el valor máximo de id_exposicion
            Query query = session.createQuery("select max(e.id.idExposicion) from Exposicion e");
            if (query.uniqueResult() == null) {
                maxIdExposicion = 0;
            } else {
                maxIdExposicion = (Integer) query.uniqueResult();
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Manejo de excepciones
        } finally {
            // Cerrar la sesión
            session.close();
        }
        return maxIdExposicion;
    }

    /**
     * Obtiene una lista de todas las exposiciones activas.
     *
     * @return Lista de exposiciones activas.
     */
    public List<Exposicion> obtenerExposicionesActivas() {
        List<Exposicion> exposiciones = null;
        Configuration config = new Configuration();
        config.configure();
        SessionFactory sessionFactory = config.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("FROM Exposicion WHERE fechBaja > current_date()");
            // Crear la consulta HQL para obtener todos los autores
            // Ejecutar la consulta y obtener la lista de exposiciones
            exposiciones = query.list();
        } catch (Exception e) {
            // Manejar cualquier excepción que pueda ocurrir durante la transacción
            e.printStackTrace();
        } finally {
            // Cerrar la sesión de Hibernate
            if (session != null) {
                session.close();
            }
        }
        return exposiciones;
    }

    /**
     * Cancela una exposición cambiando su fecha de baja a la fecha actual.
     *
     * @param idExposicion ID de la exposición a cancelar.
     */
    public void cancelarExposicion(int idExposicion) {
        Configuration config = new Configuration();
        config.configure();
        SessionFactory sessionFactory = config.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            // Crear y ejecutar la consulta HQL para actualizar la fecha de baja
            Query query = session.createQuery("UPDATE Exposicion SET fechBaja = :fechaBaja WHERE id.idExposicion = :id");
            query.setParameter("fechaBaja", new Date());
            query.setParameter("id", idExposicion);
            int result = query.executeUpdate();
            if (result > 0) {
                transaction.commit(); // Confirmar la transacción si se realizó la actualización
                System.out.println("La exposición con idExposicion " + idExposicion + " ha sido cancelada correctamente.");
            } else {
                transaction.rollback(); // Revertir la transacción si no se realizó la actualización
                System.out.println("No se encontró la exposición con idExposicion: " + idExposicion);
            }
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
    }

    /**
     * Obtiene los IDs de las obras asociadas a una exposición específica.
     *
     * @param idExposicion ID de la exposición.
     * @return Lista de IDs de las obras asociadas a la exposición.
     */
    public List<Integer> obtenerIdObrasPorIdExposicion(int idExposicion) {
        List<Integer> idObras = null;
        Configuration config = new Configuration();
        config.configure();
        SessionFactory sessionFactory = config.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            // Consulta HQL para obtener los idObra de la exposicion con el idExposicion dado
            String hql = "SELECT e.id.idObra FROM Exposicion e WHERE e.id.idExposicion = :idExposicion";
            idObras = session.createQuery(hql)
                    .setParameter("idExposicion", idExposicion)
                    .list();
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
        return idObras;
    }
}
