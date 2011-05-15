package ar.com.tsoluciones.telefront.dispatcher.remotecall;

import org.dom4j.Element;

/**
 * Copyright (c) Telefónica Soluciones
 * Todos los derechos reservados.
 */

/**
 * <p>Construye un parametro abstrayendo el tipo del cual se trata</p>
 *
 * @author despada
 * @version 1.0, May 4, 2005, 12:09:06 PM
 */
public class ParameterFactory {
	/**
	 * Construye el parametro
	 * @param parameter El elemento xml de parametro
	 * @return El parámetro pedido
	 */
	public static Parameter newInstance(Element parameter) {
		String type = parameter.attributeValue("type");

		if (type == null || type == "" || type.equalsIgnoreCase("simple"))
			return new SimpleParameter(parameter);

		if (type.equalsIgnoreCase("dto"))
			return new DtoParameter(parameter);

		if (type.equalsIgnoreCase("collection")) { 
			return new Collection(parameter);
		}
		throw new RuntimeException("El tipo de parametro (" + type + ") no existe");
	}
}
