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
import pojos.Obra;
import java.util.ArrayList;

/**
 *
 * @author Yesica
 */
public class OperacionesObras {

    /**
     * Crea una nueva obra y la guarda en la base de datos.
     *
     * @param nombre Nombre de la obra.
     * @param tipoArte Tipo de arte de la obra.
     * @param autor Autor de la obra.
     * @return Verdadero si la obra se creó correctamente, falso de lo
     * contrario.
     */
    public boolean crearObra(String nombre, String tipoArte, Autor autor) {
        boolean exito = false;
        // Obtener una sesión de Hibernate
        Configuration config = new Configuration();
        config.configure();
        SessionFactory sessionFactory = config.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            // Crear una nueva obra
            Obra obra = new Obra();
            obra.setNombre(nombre);
            obra.setTipoArte(tipoArte);
            obra.setEstado(Byte.parseByte("1"));
            obra.setAutor(autor);
            // Guardar la obra en la base de datos
            session.save(obra);
            System.out.println("Obra creada correctamente: " + obra.getNombre());
            exito = true;
            // Confirmar la transacción
            transaction.commit();
        } catch (Exception e) {
            // Manejar cualquier excepción que pueda ocurrir durante la transacción
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            // Cerrar la sesión de Hibernate
            session.close();
        }
        return exito;
    }

    /**
     * Obtiene una lista de todas las obras disponibles.
     *
     * @return Lista de obras disponibles.
     */
    public List<Obra> obtenerObrasDisponibles() {
        // Obtener una sesión de Hibernate
        Configuration config = new Configuration();
        config.configure();
        SessionFactory sessionFactory = config.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List<Obra> obrasActivas = new ArrayList<>();
        try {
            transaction = session.beginTransaction();
            // Crear la consulta para obtener las obras activas
            Query query = session.createQuery("FROM Obra WHERE estado = :estado");
            query.setParameter("estado", Byte.parseByte("1"));
            obrasActivas = query.list();
            // Confirmar la transacción
            transaction.commit();
        } catch (Exception e) {
            // Manejar cualquier excepción que pueda ocurrir durante la transacción
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            // Cerrar la sesión de Hibernate
            session.close();
        }
        return obrasActivas;
    }

    /**
     * Obtiene una lista de obras asociadas a un autor específico.
     *
     * @param id_autor ID del autor.
     * @return Lista de obras asociadas al autor.
     */
    public List<Obra> obtenerObrasPorAutor(int id_autor) {
        // Obtener una sesión de Hibernate
        Configuration config = new Configuration();
        config.configure();
        SessionFactory sessionFactory = config.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List<Obra> obras = new ArrayList<>();
        try {
            // Crear la consulta HQL para obtener todas las obras del autor
            Query query = session.createQuery("FROM Obra WHERE id_autor = :id_autor");
            query.setParameter("id_autor", id_autor);
            obras = query.list();
        } catch (Exception e) {
            // Manejar cualquier excepción que pueda ocurrir durante la transacción
            e.printStackTrace();
        } finally {
            // Cerrar la sesión de Hibernate
            if (session != null) {
                session.close();
            }
        }
        return obras;
    }

    /**
     * Obtiene una obra por su nombre.
     *
     * @param nombreObra Nombre de la obra a buscar.
     * @return Obra encontrada, o null si no se encuentra ninguna obra con ese
     * nombre.
     */
    public Obra obtenerObrasPorNombre(String nombreObra) {
        // Obtener una sesión de Hibernate
        Configuration config = new Configuration();
        config.configure();
        SessionFactory sessionFactory = config.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Obra obra = new Obra();
        try {
            // Crear la consulta HQL para obtener la obra por nombre
            Query query = session.createQuery("FROM Obra WHERE nombre = :nombreObra");
            query.setParameter("nombreObra", nombreObra);
            obra = (Obra) query.uniqueResult();
        } catch (Exception e) {
            // Manejar cualquier excepción que pueda ocurrir durante la transacción
            e.printStackTrace();
        } finally {
            // Cerrar la sesión de Hibernate
            if (session != null) {
                session.close();
            }
        }
        return obra;
    }

    /**
     * Desactiva una obra cambiando su estado a 0.
     *
     * @param idObra ID de la obra a desactivar.
     */
    public void desactivarObra(int idObra) {
        // Obtener una sesión de Hibernate
        Configuration config = new Configuration();
        config.configure();
        SessionFactory sessionFactory = config.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            // Consulta HQL para actualizar el campo estado a 0
            String hql = "update Obra set estado = :nuevoEstado where id = :idObra";
            int filasActualizadas = session.createQuery(hql)
                    .setParameter("nuevoEstado", (byte) 0)
                    .setParameter("idObra", idObra)
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            // Manejar la excepción
        } finally {
            session.close();
        }
    }

    /**
     * Activa una obra cambiando su estado a 1.
     *
     * @param idObra ID de la obra a activar.
     */
    public void ActivarObra(int idObra) {
        // Obtener una sesión de Hibernate
        Configuration config = new Configuration();
        config.configure();
        SessionFactory sessionFactory = config.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            // Consulta HQL para actualizar el campo estado a 1
            String hql = "update Obra set estado = :nuevoEstado where id = :idObra";
            int filasActualizadas = session.createQuery(hql)
                    .setParameter("nuevoEstado", (byte) 1)
                    .setParameter("idObra", idObra)
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            // Manejar la excepción
        } finally {
            session.close();
        }
    }
}
