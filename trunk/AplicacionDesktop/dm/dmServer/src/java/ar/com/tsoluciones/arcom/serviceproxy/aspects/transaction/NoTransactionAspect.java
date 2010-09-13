package ar.com.tsoluciones.arcom.serviceproxy.aspects.transaction;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import ar.com.tsoluciones.arcom.hibernate.DataSource;
import ar.com.tsoluciones.arcom.hibernate.HibernateService;
import ar.com.tsoluciones.arcom.hibernate.NoTransactional;
import ar.com.tsoluciones.arcom.logging.Log;
import ar.com.tsoluciones.arcom.logging.TSLogger;
import ar.com.tsoluciones.arcom.serviceproxy.aspects.Aspect;

/**
 * Copyright (c) Telefónica Soluciones
 * Todos los derechos reservados.
 */

/**
 * <p>
 * Cuando se configura este aspecto para un método, cancela toda transacción,
 * obligando a que no tome locks sobre la base de datos. Este aspecto es ideal
 * para aquellas queries que tomen tiempo y recorran un amplio conjunto de
 * filas.
 * </p>
 * <p>
 * No combine este aspecto con RequiredTransactionAspect
 * </p>
 *
 * @author despada
 * @version 1.0, Apr 11, 2005, 10:47:52 AM
 * @see Aspect
 */
public class NoTransactionAspect extends Aspect {
    private int transactionIsolation;
    private boolean readOnly;
    private long start;
    private boolean nested = false;
    private DataSource ds;


    @Override
	public void onBeforeMethod(Method method, Object[] args, Annotation[] annotations) {
    	start = System.currentTimeMillis();
        ds = getDataSource(method, annotations);
        Session currentSession = ds.getCurrentSession();
    	nested = currentSession.getTransaction().isActive();
    	if (!nested) {
	        try {
	            TSLogger.getLogger(HibernateService.SQL_CATEGORY_LOG)
	                .debug("********* Iniciando no transacción para método " + method.getName() + " ********* " + method.getDeclaringClass());
	            Connection connection = currentSession.connection();

	            this.transactionIsolation = connection.getTransactionIsolation();
	            this.readOnly = connection.isReadOnly();
	            connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
	            connection.setReadOnly(true);

	        } catch (HibernateException e) {
	            throw new RuntimeException("Imposible cambiar el nivel de isolation", e);
	        } catch (SQLException e) {
	            throw new RuntimeException("Imposible cambiar el nivel de isolation", e);
	        }
    	}
    }

    private DataSource getDataSource(Method method, Annotation[] annotations) {
        TSLogger log = Log.getLogger(RequiredTransactionAspect.class);
        NoTransactional noTransactional = null;
        if (annotations != null) {
            for(Annotation annot : annotations) {
                if (NoTransactional.class.isAssignableFrom(annot.getClass())) {
                    noTransactional = (NoTransactional)annot;
                    break;
                }
            }
        }
        ds = DataSource.F911;
        if (noTransactional != null) {
            ds = noTransactional.value();
            log.info(String.format("El método %s debe aplicar una transacción contra %s", method.getName(),
                        ds.toString()));
        }
        return ds;
    }
    
    /**
     * Vuelvo la conexión a su estado anterior y cierro la sesión de Hibernate.
     */
    @Override
	public void onFinally(Method method, Object[] args) {
    	if (!nested) {
            try {
                Connection connection = ds.getCurrentSession().connection();
                connection.setTransactionIsolation(transactionIsolation);
                connection.setReadOnly(readOnly);
                TSLogger.getLogger(HibernateService.SQL_CATEGORY_LOG)
                    .debug("--------- Concluyendo no transacción para método " + method.getName() + " --------- " +
                    		"(" + (System.currentTimeMillis() - start) + "ms)");
            } catch (HibernateException e) {
                throw new RuntimeException("Imposible restaurar el nivel de isolation", e);
            } catch (SQLException e) {
                throw new RuntimeException("Imposible restaurar el nivel de isolation", e);
            } finally {
                ds.close();
            }
    	}
    }

    @Override
	public Object clone() {
		Aspect aspect = new NoTransactionAspect();
		aspect.setLayerNumber(getLayerNumber());
		return aspect;
    }
}
