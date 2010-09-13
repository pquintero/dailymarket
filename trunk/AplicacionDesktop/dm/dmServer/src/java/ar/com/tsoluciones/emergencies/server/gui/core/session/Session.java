package ar.com.tsoluciones.emergencies.server.gui.core.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ar.com.tsoluciones.arcom.logging.Log;
import ar.com.tsoluciones.arcom.security.User;
import ar.com.tsoluciones.emergencies.server.gui.core.configuration.Configuration;

/**
 * Copyright (c) Telefónica Soluciones
 * Todos los derechos reservados.
 */

/**
 * <p>Representa lógicamente la sesión</p>
 *
 * @author despada
 * @version 1.0, Apr 19, 2005, 5:54:32 PM
 */
public class Session {
	private static final String USER_SESSION_KEY = "CURRENTUSER";
	private static final String WORKSTATION_SESSION_KEY = "CURRENTWORKSTATION";
	private static final String DUTY_SESSION_KEY = "CURRENTDUTY";

	private HttpSession httpSession;

	/**
	 * Crea un objeto sesion
	 *
	 * @param httpServletRequest Request de donde se saca el session
	 */
	public Session(HttpServletRequest httpServletRequest) {
		this.httpSession = httpServletRequest.getSession();
	}

	/**
	 * Crea un objeto sesion
	 *
	 * @param httpSession Sesion http
	 */
	public Session(HttpSession httpSession) {
		this.httpSession = httpSession;
	}

	/**
	 * Obtiene el usuario logeado
	 *
	 * @return El usuario logeado
	 */
	public User getUser() {
		User user = (User) httpSession.getAttribute(USER_SESSION_KEY);
		if (user == null)
			Log.getLogger(this.getClass()).warn("El usuario en la sesion es nulo");

		return user;
	}

	/**
	 * Setea el usuario en la sesion
	 *
	 * @param user Usuario a setear
	 */
	public void setUser(User user) {
		httpSession.setAttribute(USER_SESSION_KEY, user);
	}

	/**
	 * Destruye la session
	 */
	public void destroy() {
		httpSession.invalidate();
	}

	/**
	 * Obtiene el rango de minutos permitidos para las busquedas de este usuario
	 *
	 * @return Rango de minutos
	 */
	public int getMinutesRange() {
		return Configuration.getInstance().getInboxMinutes(this.getUser());
	}
}