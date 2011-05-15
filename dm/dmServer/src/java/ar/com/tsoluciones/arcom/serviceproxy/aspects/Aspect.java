package ar.com.tsoluciones.arcom.serviceproxy.aspects;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Copyright (c) Telefónica Soluciones
 * Todos los derechos reservados.
 */

/**
 * <p>La interfaz a implementar por un aspecto consumido por el ServiceFactory.</p>
 *
 * @author despada
 * @version 1.0, Apr 5, 2005, 11:16:46 AM
 * @see ar.com.tsoluciones.arcom.serviceproxy.ServiceFactory
 */
@SuppressWarnings("unused")
public abstract class Aspect implements Cloneable, Comparable<Aspect> {
	private List<Pattern> patternList = new ArrayList<Pattern>();
	private Set<String> anotaciones = new HashSet<String>();
	private Map<String, String> configurationMap = new HashMap<String, String>();
	private Integer layerNumber;
	
	/**
	 * Agrega un parametro de configuracion
	 * @param name Nombre del parametro
	 * @param value Valor del parámetro
	 */
	public void addConfiguration(String name, String value) {
		configurationMap.put(name, value);
	}

	/**
	 * Obtiene un parametro dado
	 * @param name Clave del parametro
	 * @return El valor pedido
	 */
	public String getConfiguration(String name) {
		return configurationMap.get(name);
	}

	/**
	 * Agrega un pattern a este aspecto, ampliando su foco
	 * @param pattern Pattern de foco
	 */
	public void addPattern(String pattern) {
		patternList.add(compilePattern(pattern));
	}

	/**
	 * Agrega un literal con el nombre de la clase que identifica a la anotación.
	 * @param annotation
	 */
	public void addAnnotation(String annotation) {
		anotaciones.add(annotation);
	}

	/**
	 * Verifica si el metodo recibido esta en la lista de este Aspecto. Si esta, devuelve true.
	 * @param methodName Nombre del metodo
	 * @return true si esta, false si no
	 */
	public boolean isInPattern(String methodName) {
		for (int i = 0; i < patternList.size(); i++) {
			Pattern pattern = patternList.get(i);
			Matcher matcher = pattern.matcher(methodName);
			if (matcher.find())
				return true;
		}

		return false;
	}

	/**
	 * Determina si el aspecto contiene alguna de las anotaciones pasadas.
	 * @param annotations
	 * @return true en caso afirmativo y false en caso contrario.
	 */
	public boolean contains(Annotation[] annotations) {
		for (Annotation annot : annotations) {
			if (anotaciones.contains(annot.annotationType().getName()))
				return true;
		}
		return false;
	}

	/**
	 * Compila el pattern recibido a una expresion regular y lo devuelve
	 * @param pattern Pattern en un string
	 * @return Patron de expresion regular compilado
	 */
	private Pattern compilePattern(String pattern) {
		String translatedPattern = pattern.replaceAll("\\*", "[A-Za-z0-9]*");
		Pattern regexPattern = Pattern.compile(translatedPattern);

		return regexPattern;
	}

	/**
	 * Executed before calling the method
	 * @param method Method called by reflection
	 */
	public void onBeforeMethod(Method method, Object[] args, Annotation[] annotations) {
		return;
	}

	/**
	 * Executed after calling the method
	 * @param method Method called by reflection
	 */
	public void onAfterMethod(Method method, Object[] args, Annotation[] annotations) {
		return;
	}

	/**
	 * Executed when an exception is raised from the called method
	 * @param method Method called by reflection
	 * @param e Exception raised
	 */
	public void onException(Method method, Exception e, Object[] args) {
		return;
	}

	/**
	 * Siempre se ejecuta despues de la llamada del metodo, haya excepciones o no
	 * @param method Metodo siendo ejecutado
	 */
	public void onFinally(Method method, Object[] args) {
		return;
	}

	/**
	 * Los aspectos deben saber como clonarse a si mismo y a sus objetos componentes
	 * @return Un Aspect clonado
	 */
	@Override
	public abstract Object clone();

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((anotaciones == null) ? 0 : anotaciones.hashCode());
		result = PRIME * result + ((configurationMap == null) ? 0 : configurationMap.hashCode());
		result = PRIME * result + ((patternList == null) ? 0 : patternList.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Aspect other = (Aspect) obj;
		if (anotaciones == null) {
			if (other.anotaciones != null)
				return false;
		} else if (!anotaciones.equals(other.anotaciones))
			return false;
		if (configurationMap == null) {
			if (other.configurationMap != null)
				return false;
		} else if (!configurationMap.equals(other.configurationMap))
			return false;
		if (patternList == null) {
			if (other.patternList != null)
				return false;
		} else if (!patternList.equals(other.patternList))
			return false;
		if(!layerNumber.equals(other.layerNumber))
			return false;
		
		return true;
	}

	/**
	 * Compara el aspecto en base a su orden de ejecución 
	 */
	public int compareTo(Aspect other) {		
		return layerNumber.compareTo(other.layerNumber);
	}

	public void setLayerNumber(Integer layerNumber) {
		this.layerNumber = layerNumber;
	}

	public Integer getLayerNumber() {
		return layerNumber;
	}
	
}
