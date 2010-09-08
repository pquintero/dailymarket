package ar.com.dailyMarket.services;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ar.com.dccsoft.skeleton.error.SkeletonError;

public class HibernateHelper {
	private static Logger log = Logger.getLogger(HibernateHelper.class.getName());

    private static final SessionFactory sessionFactory;
    private static final Object semaphore = new Object();

    static {
        synchronized (HibernateHelper.semaphore) {
            try {
                sessionFactory = new Configuration().configure(HibernateHelper.class.getResource("/hibernate/hibernate.cfg.xml")).buildSessionFactory();
            } catch (HibernateException ex) {
                log.fatal("No se pudo configurar el hibernate: " + ex.toString());
                throw new SkeletonError(ex);
            }
            log.debug("Hibernate Configurado.");
        }
    }

    public static final ThreadLocal session = new ThreadLocal();

    public static Session currentSession() throws HibernateException {
        Session s = (Session) session.get();
        // Open a new Session, if this Thread has none yet
        if (s == null) {
            log.debug("currentSession: Creando la sesion.");
            s = sessionFactory.openSession();
            session.set(s);
        }
        return s;
    }

    public static void closeSession() throws HibernateException {
        Session s = (Session) session.get();
        session.set(null);
        if (s != null) {
            log.debug("closeSession: Cerrando la sesion.");
            s.close();
        }
    }
}