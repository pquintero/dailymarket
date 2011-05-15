package ar.com.tsoluciones.arcom.serviceproxy.proxys;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.SortedSet;
import java.util.TreeSet;

import ar.com.tsoluciones.arcom.serviceproxy.aspects.Aspect;

/**
 * Copyright (c) Telefónica Soluciones
 * Todos los derechos reservados.
 */

/**
 * <p>
 * Clase base para todos los proxys que pueda utilizar ServiceFactory
 * </p>
 *
 * @author despada
 * @version 1.0, Apr 5, 2005, 12:54:59 PM
 * @see ar.com.tsoluciones.arcom.serviceproxy.ServiceFactory
 */
public abstract class ServiceProxy implements InvocationHandler {
    private Aspect[] aspectArray = new Aspect[0];

    protected Object serviceObject;

    /**
	 * Setea un objeto de servicio
	 * @param serviceObject Objeto se servicio
	 */
    public void setServiceObject(Object serviceObject) {
        this.serviceObject = serviceObject;
    }

    /**
	 * Inyecta los aspectos configurados
	 * @param aspectArray Aspectos configurados
	 */
    public void setAspects(Aspect[] aspectArray) {
        this.aspectArray = aspectArray;
    }

    /**
	 * Crea un objeto de esta misma clase con estado inicial
	 * @return Objeto creado
	 */
    public abstract ServiceProxy newInstance();

    /**
	 * Obtiene los aspectos construidos y configurados adecuados para un método, en orden según el atributo layerNumber. Los objetos devueltos son aspectos que
	 * pueden mantener un estado en particular para esta llamada del método. Por ello, no llame este metodo dos veces durante la misma ejecución de método, ya
	 * que obtendrá nuevos aspectos con el estado limpio.
	 * @param methodName nombre del metodo siendo ejecutado
	 * @param annotations Anotaciones asociadas al método.
	 * @return Array de aspectos
	 */
    protected Aspect[] getAspectArray(String methodName, Annotation[] annotations) {
        SortedSet<Aspect> aspectos = new TreeSet<Aspect>();

        for (int i = 0; i < aspectArray.length; i++) {
            Aspect aspect = aspectArray[i];

            if (aspect.isInPattern(methodName)) {
                aspectos.add((Aspect) aspect.clone());
            } else if (aspect.contains(annotations)) {
                aspectos.add((Aspect) aspect.clone());
            }
        }

        return aspectos.toArray(new Aspect[0]);
    }

    /**
     * Retorna la instancia del método de la clase objeto del proxy. Es necesario para levantar anotaciones
     * de las implementaciones de los servicios.
     * @param method
     * @return Method
     */
    protected Method getMethodImpl(Method method) {
        try {
            return serviceObject.getClass().getMethod(method.getName(), method.getParameterTypes());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Error al obtener el método de la clase objeto del proxy. ", e);
        }
    }

}
