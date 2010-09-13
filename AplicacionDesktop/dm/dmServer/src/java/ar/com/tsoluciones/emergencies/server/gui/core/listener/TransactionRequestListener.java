/*
 *******************************************************************************************
 *   Requerimiento:
 *******************************************************************************************
 *  Company:         Telefonica Soluciones
 *  Author:          Ipoletti
 *  Creation Date:   17/11/2004
 *  Release:	   1.0.0
 *  Description:
 *
 *
 *  Dependencies:
 *
 *****************************************************************************************
 *  History:
 *
 *  Release    Author      Date        Description
 *****************************************************************************************
 *
 * 1.0.0  Ipoletti          17/11/2004       Creacion
 *
 */
package ar.com.tsoluciones.emergencies.server.gui.core.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import ar.com.tsoluciones.arcom.hibernate.SessionContext;
import ar.com.tsoluciones.arcom.logging.Log;

/**
 * <p>  </p>
 * <p/>
 * <p> Ejemplo:
 * <pre>
 *  </pre>
 * </p>
 *
 * @author Ipoletti
 * @version 1.0, 17/11/2004
 * @see
 */
public class TransactionRequestListener implements ServletRequestListener {
	private Transaction tx = null;

	public TransactionRequestListener() {
		//
	}

	public void requestInitialized(ServletRequestEvent event) {
		cerrarSessionThread();

		HttpServletRequest req = (HttpServletRequest) event.getServletRequest();
		if (chequearRequest(req.getRequestURI())) {
			// es un abm de arcom....
			try {
				tx = SessionContext.currentSession().beginTransaction();
				log("Transaccion comenzada " + tx);
			} catch (HibernateException e) {
				Log.general.error("Error abriendo transaccion en listener", e);
			}
		}
	}

	public void requestDestroyed(ServletRequestEvent event) {
		//log("requestDestroyed...");
		if (tx != null) {
			//log("Hay transaccion abierta");
			if (chequearRequest(((HttpServletRequest) event.getServletRequest()).getRequestURI())) {
				try {
					tx.commit();
					log("Transaccion commited " + tx);
					SessionContext.closeSession();
					//log("Session cerrada");
				} catch (HibernateException e) {
					Log.general.error("Error haciendo commit en listener " + ((HttpServletRequest) event.getServletRequest()).getRequestURI(), e);
				}
			}
		}
		cerrarSessionThread();

		tx = null;
	}

	private void cerrarSessionThread() {
		Object obj = new ThreadLocal<Object>().get();
		if (obj != null) {
			try {
				log("Cerrando sesion que se encontraba abierta en thread");
				SessionContext.closeSession();
			} catch (HibernateException e) {
				Log.general.error("Error cerrando sesion abierta del thread", e);
			}
		}
	}

	private boolean chequearRequest(String uri) {
		boolean check = uri.indexOf("/abms/arcom/") != -1 && uri.toLowerCase().endsWith(".jsp");
		if (check)
			log("TransactionRequestListener ---> chequearRequest para " + uri + ": " + check);

		return check;
	}

	private void log(String l) {
		Log.general.debug("TransactionRequestListener ---> " + l);
	}
}
