package ar.com.tsoluciones.telefront.dispatcher.remotecall;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Element;

import ar.com.tsoluciones.arcom.logging.Log;
import ar.com.tsoluciones.util.Cast;

/**
 * Copyright (c) Telefónica Soluciones
 * Todos los derechos reservados.
 */

/**
 * <p>Representa un parametro que es un DTO</p>
 *
 * @author despada
 * @version 1.0, May 4, 2005, 12:02:57 PM
 */
public class DtoParameter implements Parameter {
	Map<String, String> parameterMap = new HashMap<String, String>();

	/**
	 * Construye un parametro DTO
	 * @param element Nodo del parametro
	 */
	public DtoParameter(Element element) {
		// Recorrer las propiedades del dto
		Element[] propertyArray = Cast.castList(Element.class, element.elements("property")).toArray(new Element[0]);

		if (propertyArray.length == 0)
			throw new RuntimeException("El DTO enviado no tiene propiedades asociadas");

		// Por cada prioridad, agregar un parametro
		for (int i = 0; i < propertyArray.length; i++) {
			Element property = propertyArray[i];

			String propertyValue = property.getText();
			if (propertyValue == "")
				propertyValue = null;

			parameterMap.put(property.attributeValue("name"), propertyValue);
		}
	}

	/**
	 * Obtiene el objeto que es el parametro querido
	 * @param parameterType el tipo de parametro del DTO
	 * @return Objeto parametro
	 */
	public Object getValue(Class<?> parameterType) {
		String parameterName = "";

		try {
			// Instanciar el objeto
			Object parameterTypeObject = parameterType.getConstructor(new Class[0]).newInstance(new Object[0]);

			String[] stringArray = parameterMap.keySet().toArray(new String[0]);

			// Es un DTO, buscar los setters por cada item que tengamos
			for (int i = 0; i < stringArray.length; i++) {
				parameterName = stringArray[i];

				// Tomar el valor enviado
				Object parameterValue = parameterMap.get(parameterName);
				// Buscar el setter
				String setterName = "set" + parameterName.substring(0, 1).toUpperCase() + parameterName.substring(1, parameterName.length());

				// Si el parametro tiene parentesis en el nombre, bajo convencion Struts, es componente de un map
				int openBracePosition = setterName.indexOf("(");
				int closedBracePosition = setterName.indexOf(")");
				if (openBracePosition >= 0) {
					// Tomar la key de entre los parenteis
					String key = setterName.substring(openBracePosition + 1, closedBracePosition);
					// Redefino setter (le quito los parentesis)...
					setterName = setterName.substring(0, openBracePosition);
					// ...y obtengo el getter, para obtener el hashMap y agregarle el valor
					String getterName = setterName.replaceFirst("set", "get");

					addParameterToMap(parameterTypeObject, setterName, getterName, key, parameterValue);
					continue;
				}

				// Es normal, inyectar
				injectValue(parameterTypeObject, setterName, parameterValue);
			}

			// Devolver el parametro concreto
			return parameterTypeObject;
		} catch (Exception e) {
			throw new RuntimeException("Excepción al momento de intentar construir el parametro concreto. Tipo: (" + parameterType.getName() + ") - Nombre: ("
					+ parameterName + ")", e);
		}
	}

	/**
	 * Inyecta uno de los valos recibidos en el DTO
	 * @param parameterTypeObject Instancia de la clase DTO
	 * @param setterName el nombre del setter a ejecutar para inyectar el valor
	 * @param parameterValue el valor del parametro a inyectar
	 * @throws Exception Cuando hay un problema en las llamadas del metodo
	 */
	private void injectValue(Object parameterTypeObject, String setterName, Object parameterValue) throws Exception {
		Method method;
		try {
			method = parameterTypeObject.getClass().getMethod(setterName, new Class[] { String.class });
		} catch (NoSuchMethodException e) {
			Log.getLogger(this.getClass()).debug("El objeto DTO no tiene un metodo " + setterName + " que reciba un string, ignorando");
			return;
		}

		// Invocarlo para setear el valor
		method.invoke(parameterTypeObject, new Object[] { parameterValue });
		Log.getLogger(this.getClass()).debug("Asignado valor a propiedad DTO mediante setter " + setterName);
	}

	/**
	 * Agrega un parametro a un map en el DTO
	 * @param parameterTypeObject Objeto DTO
	 * @param getterName Nombre del getter a llamar para obtener el map
	 * @param setterName nombre del setter que se usa para reinyectar el mapa
	 * @param key Clave en el mapa
	 * @param parameterValue Valor a agregar
	 */
	private void addParameterToMap(Object parameterTypeObject, String setterName, String getterName, String key, Object parameterValue) throws Exception {
		// Obtener el getter
		Method method;
		try {
			method = parameterTypeObject.getClass().getMethod(getterName, new Class[0]);
		} catch (NoSuchMethodException e) {
			Log.getLogger(this.getClass()).debug("El objeto DTO no tiene un metodo " + getterName + " que devuelva un map, ignorando");
			return;
		}

		// Tomar el mapa del getter
		Map<String, Object> map = Cast.castMap(String.class, Object.class, (Map<?,?>) method.invoke(parameterTypeObject, new Object[0]));
		if (map == null)
			map = new HashMap<String, Object>();
		// y agregarle este valor
		Log.getLogger(this.getClass()).debug("Asignado en map valor " + parameterValue + " con key " + key);
		map.put(key, parameterValue);

		// Por ultimo, volver a inyectar el map
		try {
			method = parameterTypeObject.getClass().getMethod(setterName, new Class[] { Map.class });
		} catch (NoSuchMethodException e) {
			Log.getLogger(this.getClass()).debug("El objeto DTO no tiene un metodo " + setterName + " que reciba un Map, ignorando");
			return;
		}

		// Invocarlo para setear el valor
		method.invoke(parameterTypeObject, new Object[] { map });
		Log.getLogger(this.getClass()).debug("Asignado valor a propiedad DTO mediante setter " + setterName);
	}
}
