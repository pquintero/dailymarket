package ar.com.tsoluciones.telefront.servicefactory;

import ar.com.tsoluciones.arcom.security.User;
import ar.com.tsoluciones.arcom.serviceproxy.ServiceFactory;
import ar.com.tsoluciones.emergencies.server.gui.core.session.Session;
import ar.com.tsoluciones.util.UserManager;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

/**
 * Copyright (c) Telefónica Soluciones
 * Todos los derechos reservados.
 */

/**
 * <p>
 * Extiende ServiceFactory, proporcionandole algunos datos extra que tienen que
 * ver con el entorno web.
 * </p>
 * <p>
 * A diferencia de ServiceFactory, esta clase deberia ser extendida en la misma
 * clase que implementa los servicios, para mantener las cosas simples.
 * </p>
 * <p/>
 * <p>
 * La idea no es que TelefrontServiceFactory presente una interfaz de servicios
 * debilmente acoplada a la web, sino lo contrario. Cada acción que implemente
 * esta clase debe presentar un juego de operaciones preparado para una página
 * en especial, con dos objetivos:
 * </p>
 * <p>
 * a) Arrastrar la mayor cantidad de funcionalidad hacia java puro b) Facilitar
 * el trabajo en javascript del cliente.
 * </p>
 *
 * @author despada
 * @version 1.0, Nov 19, 2004, 2:48:54 PM
 * @see ServiceFactory
 * @since 1.0
 */
public abstract class TelefrontServiceFactory extends ServiceFactory {
    private HttpServletRequest httpServletRequest;

    /**
     * Obtiene el request
     *
     * @return el request
     */
    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    /**
     * Obtiene la sesión http
     *
     * @return Sesion HTTP
     */
    public HttpSession getHttpSession() {
        return httpServletRequest.getSession();
    }

    /**
     * Setea el request HTTP
     *
     * @param httpServletRequest
     *            request HTTP
     */
    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    /**
     * Antes de la invocación a un método invocado exteriormente se setea el usuario actual
     * al UserManager.
     */
    public void beforeExecution() {
        Session session = new Session(getHttpSession());
        UserManager.setCurrentUser(session.getUser());
    }

    /**
     * Después de la invocación a un método invocado exteriormente libera el usuario actual
     * del UserManager.
     */
    public void afterExecution() {
        UserManager.removeCurrentUser();
    }
    
	/**
	 * Retorna el usuario sesionado.
	 */
	protected User getCurrentUser() {
		return new Session(getHttpSession()).getUser();
	}

}
