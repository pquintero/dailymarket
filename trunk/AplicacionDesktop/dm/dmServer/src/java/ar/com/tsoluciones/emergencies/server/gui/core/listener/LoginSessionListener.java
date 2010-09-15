package ar.com.tsoluciones.emergencies.server.gui.core.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import ar.com.tsoluciones.arcom.logging.Log;
import ar.com.tsoluciones.arcom.security.User;
import ar.com.tsoluciones.emergencies.server.gui.core.session.Session;

/**
 * LoguinSessionListener.java
 * Copyright (c) Telefónica Soluciones
 * Todos los derechos reservados.
 */

/**
 * <p>
 * Escucha los eventos de sesion creada / destruida, y desasigna la workstation
 * </p>
 *
 * @author atoranzo
 * @version 02/06/2005, 16:09:07
 * @see
 */
public class LoginSessionListener implements HttpSessionListener {

	/**
	 * Método sin implementación
	 *
	 * @param event Evento
	 */
	public void sessionCreated(HttpSessionEvent event) {
		//
	}

	/**
	 * Cuando se destruye la session se desloguea al user de la Workstation
	 *
	 * @param event Evento
	 */
	public void sessionDestroyed(HttpSessionEvent event) {
		Session session = new Session(event.getSession());

		// Tomar datos de la session a matar
		User user = session.getUser();

		// Chequeos informativos
		if (user == null) {
			Log.getLogger(this.getClass()).warn("Usuario nulo al eliminar su sesion - esto solo es normal si reinicio el tomcat sin persistencia de sesiones");
			return;
		}

		Log.getLogger(this.getClass()).debug("Sesion eliminada para el usuario " + user.getUser()
				);

	}
}
