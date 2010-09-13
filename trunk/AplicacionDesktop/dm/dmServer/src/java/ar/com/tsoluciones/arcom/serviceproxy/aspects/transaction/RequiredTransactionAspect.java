package ar.com.tsoluciones.arcom.serviceproxy.aspects.transaction;

import ar.com.tsoluciones.arcom.hibernate.DataSource;
import ar.com.tsoluciones.arcom.hibernate.HibernateService;
import ar.com.tsoluciones.arcom.hibernate.Transactional;
import ar.com.tsoluciones.arcom.logging.Log;
import ar.com.tsoluciones.arcom.logging.TSLogger;
import ar.com.tsoluciones.arcom.serviceproxy.aspects.Aspect;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;


/**
 * Copyright (c) Telefónica Soluciones
 * Todos los derechos reservados.
 */

/**
 * <p>
 * Cuando se asigna este aspecto a un método, coordina su transacción, comenzándola, confirmándola
 * o cancelándola según sea necesario.
 * </p>
 *
 * @author despada
 * @version 1.0, Mar 1, 2005, 10:50:49 AM
 * @see Aspect
 * @since 1.0.0.0
 */
public class RequiredTransactionAspect extends Aspect {
    private DataSource ds;
    private Transaction transaction;
    private boolean nested = false;
    private long start;

    @Override
	public void onBeforeMethod(Method method, Object[] args, Annotation[] annotations) {
    	start = System.currentTimeMillis();
        TSLogger logger = Log.getLogger(RequiredTransactionAspect.class);
        String methodClass = method.getName() + "*******" + method.getDeclaringClass();
        ds = getDataSource(method, annotations);
        Session currentSession = ds.getCurrentSession();
        nested = currentSession.getTransaction().isActive();
        if (!nested) {
            logger.debug("Comenzando transacción para método " + methodClass);
            TSLogger.getLogger(HibernateService.SQL_CATEGORY_LOG)
                .debug("********* Iniciando transacción para método " + methodClass);
            try {
                transaction = ds.getCurrentSession().beginTransaction();
            } catch (HibernateException e) {
                throw new RuntimeException("Error al intentar abrir transacción", e);
            }
            logger.debug("Se inició la transacción para el método " + method.getName());
        } else {
        	TSLogger.getLogger(HibernateService.SQL_CATEGORY_LOG).debug("Transacción anidada para " + methodClass);
        }


    }

    private DataSource getDataSource(Method method, Annotation[] annotations) {
        TSLogger log = Log.getLogger(RequiredTransactionAspect.class);
        Transactional transactional = null;
        if (annotations != null) {
            for(Annotation annot : annotations) {
                if (Transactional.class.isAssignableFrom(annot.getClass())) {
                    transactional = (Transactional)annot;
                    break;
                }
            }
        }
        ds = DataSource.F911;
        if (transactional != null) {
            ds = transactional.value();
            log.info(String.format("El método %s debe aplicar una transacción contra %s", method.getName(),
                        ds.toString()));
        }
        return ds;
    }

    @Override
	public void onAfterMethod(Method method, Object[] args, Annotation[] annotations) {
        TSLogger logger = Log.getLogger(RequiredTransactionAspect.class);
        if (!nested) {
            logger.debug("Confirmando transacción para metodo " + method.getName() + " ----- " + method.getDeclaringClass());
            try {
                if (transaction.isActive())
                    transaction.commit();
                else
                    logger.warn("Se intentó hacer commit de una transacción no activa. Método " + method.getName());
                TSLogger.getLogger(HibernateService.SQL_CATEGORY_LOG)
                    .debug("--------- Commiteando transacción para método " + method.getName() +
                    		" --------- ("+ (System.currentTimeMillis() - start) + "ms)");
            } catch (HibernateException e) {
                throw new RuntimeException("Error al intentar confirmar transacción en el método " + method, e);
            }
        } else {
        	TSLogger.getLogger(HibernateService.SQL_CATEGORY_LOG).debug("Transacción anidada para commit en: " + method.getName());
        }

    }

    @Override
	public void onException(Method method, Exception e, Object[] args) {
        TSLogger logger = Log.getLogger(RequiredTransactionAspect.class);
        if (!nested) {
        	logger.error("Cancelando transacción para metodo " + method.getName());
        	logger.error("Haciendo rollback", e);
            try {
                if (transaction.isActive()) {
                    transaction.rollback();
                    logger.warn("Se hizo rollback de la transacción para el método " + method.getName());
                } else {
                    logger.warn("Se intentó hacer rollback de una transacción no activa. Método " + method.getName());
                    logger.warn("¿Fue rollbacked? " + transaction.wasRolledBack());
                }
            } catch (HibernateException he) {
                throw new RuntimeException("Error al intentar cancelar transacción", he);
            }
        }
    }

    /**
     * Cierro la sesión obtenida en el método onBeforeMethod
     */
    @Override
	public void onFinally(Method method, Object[] args) {
    	if (!nested) {
    		ds.close();
    	}
    }

    @Override
	public Object clone() {
		Aspect aspect = new RequiredTransactionAspect();
		aspect.setLayerNumber(getLayerNumber());
		return aspect;
    }
}
