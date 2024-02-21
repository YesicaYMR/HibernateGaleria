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


/**
 *
 * @author Yesica
 */
public class OperacionesAutor {

    /**
     * Crea un nuevo autor y lo guarda en la base de datos.
     *
     * @param nombre Nombre del autor a crear.
     * @param anno Año de nacimiento del autor.
     * @param nacionalidad Nacionalidad del autor.
     */
    public void crearAutor(String nombre, String anno, String nacionalidad) {
        Configuration config = new Configuration();
        config.configure();
        SessionFactory sessionFactory = config.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            // Crear un objeto Autor con los datos ingresados por el usuario
            Autor autor = new Autor();
            autor.setNombre(nombre);
            autor.setFechNacimiento(anno);
            autor.setNacionalidad(nacionalidad);
            // Guardar el autor en la base de datos
            session.save(autor);
            // Confirmar la transacción
            transaction.commit();
        } catch (Exception e) {
            // Manejar cualquier excepción que pueda ocurrir durante el proceso
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error al crear el autor");
        } finally {
            // Cerrar la sesión de Hibernate
            session.close();
        }
    }

    /**
     * Elimina un autor de la base de datos.
     *
     * @param id ID del autor a eliminar.
     * @return 0 si el autor se eliminó correctamente, 1 si tiene obras
     * asociadas, 2 si el autor no existe.
     */
    public int eliminarAutor(String id) {
        Configuration config = new Configuration();
        config.configure();
        SessionFactory sessionFactory = config.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        int exito = -1;
        try {
            transaction = session.beginTransaction();
            // Verificar si hay alguna obra asociada al autor
            Query queryObra = session.createQuery("SELECT COUNT(*) FROM Obra WHERE id_autor = :idAutor");
            queryObra.setParameter("idAutor", id);
            long cantidadObras = (long) queryObra.uniqueResult();
            if (cantidadObras > 0) {
                exito = 1;
            } else {
                // Buscar al autor por ID
                Query query = session.createQuery("FROM Autor WHERE id_autor = :idAutor");
                query.setParameter("idAutor", id);
                Autor autor = (Autor) query.uniqueResult();
                // Verificar si el autor existe
                if (autor != null) {
                    // Eliminar el autor
                    session.delete(autor);
                    exito = 0;
                } else {
                    exito = 2;
                }
            }
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
     * Obtiene una lista de todos los autores.
     *
     * @return Lista de autores.
     */
    public List<Autor> obtenerTodosLosAutores() {
        List<Autor> autores = null;
        Configuration config = new Configuration();
        config.configure();
        SessionFactory sessionFactory = config.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            Query query = session.createQuery("FROM Autor");
            // Ejecutar la consulta y obtener la lista de autores
            autores = query.list();
        } catch (Exception e) {
            // Manejar cualquier excepción que pueda ocurrir durante la transacción
            e.printStackTrace();
        } finally {
            // Cerrar la sesión de Hibernate
            if (session != null) {
                session.close();
            }
        }
        return autores;
    }
    
     /**
     * Obtiene una obra por su nombre.
     *
     * @param nombreObra Nombre de la obra a buscar.
     * @return Obra encontrada, o null si no se encuentra ninguna obra con ese
     * nombre.
     */
    public int obtenerIdPorNombre(String nombreAutor) {
        // Obtener una sesión de Hibernate
        Configuration config = new Configuration();
        config.configure();
        SessionFactory sessionFactory = config.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        int idAutor = -1;
        try {
            // Crear la consulta HQL para obtener la obra por nombre. si no existe devuelve null y eso significa que no hay nadie con ese nombre
            
            Query query = session.createQuery("SELECT a.idAutor FROM Autor a WHERE a.nombre = :nombreAutor");
            query.setParameter("nombreAutor", nombreAutor);
            if(query.uniqueResult()==null)
            idAutor = 0;//no encontrado
            else
                idAutor=1;//encontrado
        } catch (Exception e) {
            // Manejar cualquier excepción que pueda ocurrir durante la transacción
            e.printStackTrace();
        } finally {
            // Cerrar la sesión de Hibernate
            if (session != null) {
                session.close();
            }
        }
        return idAutor;
    }

}
