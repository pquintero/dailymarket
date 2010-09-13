package ar.com.tsoluciones.telefront.dispatcher.remotecall;

import org.dom4j.Element;

import java.lang.reflect.Constructor;

/**
 * Copyright (c) Telefónica Soluciones
 * Todos los derechos reservados.
 */

/**
 * <p>Un parametro que es un objeto Java construible a partir de un String</p>
 *
 * @author despada
 * @version 1.0, May 4, 2005, 12:02:47 PM
 */
public class SimpleParameter implements Parameter
{
	String value;

	/**
	 * Construye el parámetro
	 * @param element Nodo parameter del xml
	 */
	public SimpleParameter(Element element)
	{
		String value = element.getText();
		if (value == "") value = null;

		this.value = value;
	}

	/**
	 * Obtiene el valor del parametro
	 * @return Valor del parametro
	 */
	public Object getValue(Class<?> parameterType)
	{
		try
		{
      if (value == null) return null;
			
			// Buscar constructor por string
			Constructor<?> constructor = parameterType.getConstructor(new Class[] { String.class });
			// Instanciar
			Object object = constructor.newInstance(new Object[] { this.value });
			// Devolver el parametro concreto
			return object;
		}
		catch(Exception e)
		{
			throw new RuntimeException("Excepción al momento de intentar construir el parametro concreto", e);
		}
	}
}
