package ar.com.tsoluciones.arcom.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import ar.com.tsoluciones.arcom.system.SystemManager;

/**
 * Clase contenedora de las sesiones vinculadas a los threads que acceden a la capa de persistencia.
 * Para un tratamiento seguro en este acceso concurrente se usan variables thread-local para garantizar
 * un objeto de Session de Hibernate distinto según el thread que la está accediendo.
 */
public class SessionContext {
    /**
     * Session local para F911
     */
	public static final ThreadLocal<Session> session = new ThreadLocal<Session>();

	/**
	 * Obtiene la session de hibernate asociada al ThreadLocal Se debe tener en
	 * cuenta que este metodo no debe utilizarse en paquetes de negocios Es
	 * solamente para que lo utilice la capa de persistencia
	 *
	 * @return la session hibernate asociada con el ThreaLocal
	 * @throws HibernateException en caso de no poder instanciar la session hibernate
	 */
	public static Session currentSession() throws HibernateException {
		Session s = session.get();
		if (s == null) {
			SessionFactory sf = SystemManager.getSessionFactory();
			s = sf.openSession();
			session.set(s);
		}
		return s;
	}

	/**
	 * Cierra las sessiones, las quita del ThreadLocal
	 *
	 * @throws HibernateException en caso de no poder cerrar la session
	 */
	public static void closeSession(){
	    close(session);
	}

    private static void close(ThreadLocal... contenedores) {
        for(ThreadLocal<Session> contenedor : contenedores) {
            Session s = contenedor.get();
            contenedor.set(null);
            if (s != null)
                s.close();
        }
    }

    public static void closeF911() {
        close(session);
    }

	public static boolean sessionThreadCreated() {
		return session.get() != null;
	}
}
