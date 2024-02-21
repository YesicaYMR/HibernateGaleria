/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import pojos.NewHibernateUtil;

        

/**
 *
 * @author Yesica
 */
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    static {
        try {
            // Cargar la configuración de Hibernate desde hibernate.cfg.xml
            Configuration configuration = new Configuration().configure();
            // Crear la factoría de sesiones
            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            // Manejar cualquier error en la inicialización
            System.err.println("Error al crear la factoría de sesiones: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    // Método para obtener una sesión de Hibernate
    public static Session getSession() {
        return sessionFactory.openSession();
    }

    // Método para cerrar la factoría de sesiones
    public static void shutdown() {
        sessionFactory.close();
    }
}
