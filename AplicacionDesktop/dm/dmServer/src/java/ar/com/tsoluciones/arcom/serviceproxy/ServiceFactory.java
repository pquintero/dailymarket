package ar.com.tsoluciones.arcom.serviceproxy;

import ar.com.tsoluciones.arcom.serviceproxy.proxys.BasicServiceProxy;
import ar.com.tsoluciones.arcom.serviceproxy.proxys.ServiceProxy;

import java.lang.reflect.Proxy;


/**
 * ServiceFactory.java
 * Copyright (c) Telefónica Soluciones
 * Todos los derechos reservados.
 */

/**
 * <p>
 * Clase base para los factory de servicios
 * </p>
 *
 * @author despada
 * @version 1.0, Nov 12, 2004, 11:28:49 AM
 * @see BasicServiceProxy
 * @since 2.2
 */
public abstract class ServiceFactory
{
  public abstract Object newInstance();

	/**
	 * Se pone como proxy de una determinada interfaz, construye la misma y la retorna.
	 *
	 * @param serviceObject objeto cuyos llamados se interceptan
	 * @return Interfaz construida
	 */
	protected Object newServiceInstance(Object serviceObject)
	{
		// Llamar metodo
		ServiceProxy serviceProxy = Configuration.getInstance().getServiceProxy(serviceObject);
		return Proxy.newProxyInstance(serviceObject.getClass().getClassLoader(), serviceObject.getClass().getInterfaces(),  serviceProxy);
	}
}
