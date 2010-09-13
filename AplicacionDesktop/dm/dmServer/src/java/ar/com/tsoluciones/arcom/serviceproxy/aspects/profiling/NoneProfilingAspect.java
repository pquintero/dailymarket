package ar.com.tsoluciones.arcom.serviceproxy.aspects.profiling;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import ar.com.tsoluciones.arcom.logging.Log;
import ar.com.tsoluciones.arcom.serviceproxy.aspects.Aspect;

/**
 * Copyright (c) Telefónica Soluciones
 * Todos los derechos reservados.
 */

/**
 * <p>Implementacion de profiling que solo brinda servicios basicos</p>
 *
 * @author despada
 * @version 1.0, Mar 1, 2005, 11:22:46 AM
 * @see Aspect
 */
public class NoneProfilingAspect extends Aspect {

	@Override
	public void onBeforeMethod(Method method, Object[] args, Annotation[] annotations) {
		Log.getLogger(this.getClass()).info("Invocando metodo " + method.getName() + " de clase " + method.getDeclaringClass().getName());
	}

	@Override
	public void onAfterMethod(Method method, Object[] args, Annotation[] annotations) {
		Log.getLogger(this.getClass()).info("Culminando invocacion metodo" + method.getName() + " de clase " + method.getDeclaringClass().getName());
	}

	@Override
	public Object clone() {
		Aspect aspect = new NoneProfilingAspect();
		aspect.setLayerNumber(getLayerNumber());
		return aspect;
	}
}
