package ar.com.tsoluciones.arcom.serviceproxy.aspects.profiling;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Calendar;

import org.apache.log4j.Logger;

import ar.com.tsoluciones.arcom.logging.Log;
import ar.com.tsoluciones.arcom.serviceproxy.aspects.Aspect;
import ar.com.tsoluciones.emergencies.server.gui.core.configuration.Configuration;
import ar.com.tsoluciones.util.UserManager;

/**
 * Copyright (c) Telefónica Soluciones
 * Todos los derechos reservados.
 */

/**
 * <p>Define un profiler basico de metodos</p>
 *
 * @author despada
 * @version 1.0, Mar 1, 2005, 11:15:55 AM
 * @since 1.0.0.0
 */
public class BasicProfilingAspect extends Aspect {
	
	private static final String EXCEEDEDOPERATIONS = "ExceededOperations";
	private static final Logger exceededOperationsLog = Logger.getLogger(EXCEEDEDOPERATIONS);
	
	Calendar calendar;

	long executionTime = -1;

	/**
	 * Obtiene el tiempo de ejecucion en milisegundos
	 * @return Devuelve el tiempo de ejecucion en milisegundos
	 */
	@Override
	public String toString() {
		if (executionTime == -1)
			throw new RuntimeException("Debe primero culminar el llamado del metodo con afterMethodCall antes de obtener este valor");

		return ""+executionTime;
	}

	@Override
	public void onBeforeMethod(Method method, Object[] args, Annotation[] annotations) {
		Log.getLogger(this.getClass()).info("Proxy invocando método " + method.getName() + " de clase " + method.getDeclaringClass().getName());
		calendar = Calendar.getInstance();
	}

	@Override
	public void onFinally(Method method, Object[] args) {
		executionTime = Calendar.getInstance().getTimeInMillis() - calendar.getTimeInMillis();
		//Si la operación se excede del tiempo configurado
		String usuario = "";
		if(UserManager.getCurrentUser()!=null){
//			usuario = "Usuario: " + UserManager.getCurrentUser().getUsername();
		}
		if(executionTime >= Configuration.getInstance().getMaxTimeForExecuteOperation()*1000){
			exceededOperationsLog.info("Proxy culminó de invocar al método " + method.getName() + " tomó " + executionTime + " milisegundos. " + usuario);
		}
		
	}

	@Override
	public Object clone() {
		Aspect aspect = new BasicProfilingAspect();
		aspect.setLayerNumber(getLayerNumber());
		return aspect;
	}
}
