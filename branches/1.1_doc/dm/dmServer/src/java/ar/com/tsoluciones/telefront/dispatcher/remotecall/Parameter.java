package ar.com.tsoluciones.telefront.dispatcher.remotecall;

/**
 * Copyright (c) Telefónica Soluciones
 * Todos los derechos reservados.
 */

/**
 * <p>La funcionalidad a implementar por una clase que abstrae un tipo de parametro de llamada remota</p>
 *
 * @author despada
 * @version 1.0, May 4, 2005, 12:00:48 PM
 */
public interface Parameter
{
	/**
	 * Obtiene el parametro como un objeto Java
	 * @param parameterType el tipo del parametro
	 * @return Objeto java
	 */
	public Object getValue(Class<?> parameterType);
}
