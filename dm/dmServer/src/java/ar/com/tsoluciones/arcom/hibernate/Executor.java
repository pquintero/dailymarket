package ar.com.tsoluciones.arcom.hibernate;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import ar.com.tsoluciones.arcom.cor.InternalErrorException;
import ar.com.tsoluciones.arcom.logging.TSLogger;

/**
 * Esta clase utilitaria permite ejecutar un bloque de código en un contexto transaccional.
 * Para ello simplemente hay que pasar una instancia de la clase abstracta Execution&lt;T&gt; que contenga el bloque
 * que requiere ser transaccional.
 *
 */
public class Executor {

    /**
     * <p>
     * Método genérico que demarca y ejecuta el bloque. Permite que la transacción utilice una conexión seteando el 
     * isolation level en Conection.READ_UNCOMMITTED lo cual supone menos lockeo sobre las tablas involucradas. 
     * </p>
     * @param <T> Tipo genérico de retorno resultante de la ejecución del bloque.
     * @param e Instancia de Execution.
     * 
     * @return Una instancia del tipo T
     */
	public static <T> T execute(Execution<T> e, Boolean readUncommitted) {
		int transactionIsolation = Connection.TRANSACTION_READ_COMMITTED;
	    boolean readOnly = false;	    
        Logger logger = Logger.getLogger(Executor.class);
		Session s = e.getSession();
        Transaction transaction = s.getTransaction();
        boolean nested = false;
        long start = System.currentTimeMillis();
		try {
			nested = transaction.isActive();
			if (!nested) {
				if (readUncommitted) {
					Connection conn = s.connection();
					transactionIsolation = conn.getTransactionIsolation();
					readOnly = conn.isReadOnly();
					conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
					conn.setReadOnly(true);
				}
				transaction = s.beginTransaction();
			} else {
				logger.debug("Transacción anidada");
			}
				
			logger.debug("Iniciando transacción");
			if (e.actionName != null)
				TSLogger.getLogger(HibernateService.SQL_CATEGORY_LOG).debug("******** Iniciando transacción en Executor para " + e.actionName);
			T retValue = e.block();
			if (!nested)
				transaction.commit();
			if (e.actionName != null)
				TSLogger.getLogger(HibernateService.SQL_CATEGORY_LOG).debug("-------- Hice commit de la transacción en el Executor para " + e.actionName + 
						"(" + (System.currentTimeMillis() - start)+ "ms)");
			logger.debug("Commit");
			return retValue;
		} catch (Throwable t) {
            if (transaction != null && transaction.isActive() && !nested)
                transaction.rollback();
			logger.debug("Haciendo Rollback");
			if (e.actionName != null)
				TSLogger.getLogger(HibernateService.SQL_CATEGORY_LOG).debug("!!!!!!! Rollback en el Executor para " + e.actionName);
			throw new InternalErrorException("Se produjo un error en la transacción. Se hizo rollback", t);
		} finally {
			if (!nested) {
				if (readUncommitted) {
	                Connection conn = s.connection();
	                try {
		                conn.setTransactionIsolation(transactionIsolation);
		                conn.setReadOnly(readOnly);
	                } catch (SQLException sqle) {
	                	throw new RuntimeException("Imposible restaurar el isolation level de la conexión", sqle);
	                }
					
				}
				e.closeSession();
			}
				
        }		
	}
	
    /**
     * Método genérico que demarca y ejecuta el bloque.
     * @param <T> Tipo genérico de retorno resultante de la ejecución del bloque.
     * @param e Instancia de Execution.
     * @return Una instancia del tipo T
     */
	public static <T> T execute(Execution<T> e){
		return execute(e, false);
	}

    /**
     * Clase base para los bloques de ejecución que requieran ejecutarse en un contexto transaccional.
     *
     * @param <T> Tipo genérico de retorno resultante de la ejecución del bloque.
     */
	public static abstract class Execution<T> {
		protected DataSource ds;
		protected String actionName;
		protected String datasourceName = null;

        public Execution(DataSource ds) {
			this.ds = ds;
		}
        
        public Execution(DataSource ds, String dynamicDSName) {
			this.ds = ds;
			datasourceName = dynamicDSName;
		}
        
        /**
         * En este método va el código a ejecutar.
         */
		public abstract T block();

	    /**
	     * Obtiene la sesión de hibernate vinculada al thread actual.
	     * @return Session
	     */
		public Session getSession() {
			if (datasourceName != null) {
				return ds.getCurrentSession(datasourceName);
			} else
				return ds.getCurrentSession();
		}

        /**
         * Cierra la sesión usada
         */
        public void closeSession() {
            ds.close();
        }

        public void setActionName(String s) {
        	this.actionName = s;
        }
	}
}


