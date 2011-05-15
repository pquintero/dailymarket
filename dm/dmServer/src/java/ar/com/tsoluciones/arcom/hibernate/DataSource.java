package ar.com.tsoluciones.arcom.hibernate;

import org.hibernate.Session;

/**
 * Datasources accedidos por la aplicación.
 */
public enum DataSource {
    F911 {
        
    	@Override
		public Session getCurrentSession(String name) {
            return getCurrentSession();
        }
    	
    	@Override
		public Session getCurrentSession() {
            return SessionContext.currentSession();
        }

        @Override
		public void close() {
            SessionContext.closeF911();
        }
    };

    public abstract Session getCurrentSession();

    public abstract Session getCurrentSession(String name);
    
    public abstract void close();
}
