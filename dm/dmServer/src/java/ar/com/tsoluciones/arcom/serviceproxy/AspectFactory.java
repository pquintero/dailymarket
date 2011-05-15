package ar.com.tsoluciones.arcom.serviceproxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import ar.com.tsoluciones.arcom.serviceproxy.aspects.Aspect;

/**
 * Copyright (c) Telefónica Soluciones
 * Todos los derechos reservados.
 */

/**
 * <p>Construye un aspecto y lo configura</p>
 *
 * @author despada
 * @version 1.0, Apr 5, 2005, 11:38:11 AM
 * @see Configuration
 */
public class AspectFactory {
	/**
	 * Construye un aspecto
	 * @param layerNumber Orden en el que debe ser ejecutado el aspecto
	 * @param implementation Clase que implementa el aspecto
	 * @return Aspecto construido
	 */
	public static Aspect construct(String layerNumber, String implementation) {
		try {
			// Instanciar el aspecto
			Class<?> aspectClass = Class.forName(implementation);

			Constructor<?> constructor = aspectClass.getDeclaredConstructor(new Class[0]);
			Aspect aspect = (Aspect) constructor.newInstance(new Object[0]);
			aspect.setLayerNumber(Integer.valueOf(layerNumber));
			
			return aspect;
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("No se encontro el aspecto " + implementation, e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("El aspecto no tiene un constructor sin parámetros", e);
		} catch (InstantiationException e) {
			throw new RuntimeException("El aspecto no pudo ser instanciado", e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException("El aspecto no pudo ser instanciado", e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("El aspecto no pudo ser instanciado", e);
		} catch (NumberFormatException e) {
			throw new RuntimeException("El número de layer es inválido para el aspecto", e);
		}
	}
}