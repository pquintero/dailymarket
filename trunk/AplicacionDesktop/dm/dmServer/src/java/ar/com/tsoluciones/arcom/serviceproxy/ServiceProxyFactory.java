package ar.com.tsoluciones.arcom.serviceproxy;

import ar.com.tsoluciones.arcom.serviceproxy.proxys.ServiceProxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Copyright (c) Telefónica Soluciones
 * Todos los derechos reservados.
 */

/**
 * <p>Se encarga de construir un objeto ServiceProxy</p>
 *
 * @author despada
 * @version 1.0, Apr 5, 2005, 2:44:09 PM
 * @see ar.com.tsoluciones.arcom.serviceproxy.proxys.ServiceProxy
 */
public class ServiceProxyFactory {
	/**
	 * Construye un proxy
	 * @param implementation Clase que implementa el proxy
	 * @return Proxy construido
	 */
	public static ServiceProxy construct(String implementation) {
		try {
			// Instanciar el aspecto
			Class<?> serviceProxyClass = Class.forName(implementation);

			Constructor<?> constructor = serviceProxyClass.getConstructor(new Class[0]);
			ServiceProxy serviceProxy = (ServiceProxy) constructor.newInstance(new Object[0]);

			return serviceProxy;
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("No se encontro el proxy" + implementation, e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("El proxy no tiene un constructor sin parametros", e);
		} catch (InstantiationException e) {
			throw new RuntimeException("El proxy no pudo ser instanciado", e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException("El proxy no pudo ser instanciado", e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("El proxy no pudo ser instanciado", e);
		}
	}
}