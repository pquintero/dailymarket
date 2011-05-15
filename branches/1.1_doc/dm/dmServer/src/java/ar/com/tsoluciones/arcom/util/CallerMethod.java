package ar.com.tsoluciones.arcom.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import ar.com.tsoluciones.arcom.logging.Log;

/**
 * <p> </p>
 * <p>Clase para invocar metodos: dada una clase, dado un objeto, a traves de un factory.</p>
 * <p>En caso de acceder por factory, se debe pasar el metodo del factory y el metodo de la clase que devuelve el factory.</p>
 * <p>Ejecuta los métodos, sin importar si son static o no.</p>
 * @author Ariel Clocchiatti
 * @version 1.0
 */

public class CallerMethod {

	public static Object invokeFromClass(String className, String methodName, Object[] args) throws Exception {

		Object o = null;
		Class<?>[] argsC = null;

		if (args != null) {
			argsC = new Class[args.length];
			for (int i = 0; i < args.length; i++) {
				argsC[i] = args[i].getClass();
			}
		}
		Object instance = null;
		Class<?> clase = Class.forName(className);
		Method metodo = clase.getMethod(methodName, argsC);

		if (!Modifier.isStatic(metodo.getModifiers())) {
			instance = clase.newInstance();
		}
		o = metodo.invoke(instance, args);
		return o;

	}

	public static Object invokeFromFactory(String factoryName, String factoryMethod, Object[] factoryArgs, String methodName, Object[] args) throws Exception {

		Object res = null;
		Object impl = null;

		Class<?>[] argsC = null;
		try {
			if (factoryArgs != null) {
				argsC = new Class[factoryArgs.length];
				for (int i = 0; i < factoryArgs.length; i++) {
					argsC[i] = factoryArgs[i].getClass();
				}
			}
			Object instance = null;
			Class<?> clase = Class.forName(factoryName);
			Method metodoFactory = clase.getMethod(factoryMethod, argsC);

			if (!Modifier.isStatic(metodoFactory.getModifiers())) {
				instance = clase.newInstance();
			}
			impl = metodoFactory.invoke(instance, factoryArgs);
			//**************************************************************
			argsC = null;
			if (args != null) {
				argsC = new Class[args.length];
				for (int i = 0; i < args.length; i++) {
					argsC[i] = args[i].getClass();
				}
			}

			Method metodo = impl.getClass().getMethod(methodName, argsC);

			res = metodo.invoke(impl, args);
		} catch (Exception h) {
			Log.general.error(h.getMessage(), h);
			throw h;
		}
		return res;
	}

	public static Object invokeFromObject(Object instance, String methodName, Object[] args) throws Exception {
		Object res = null;
		if (instance == null) {
			return null;
		}
		Class<?>[] argsC = null;
		try {
			if (args != null) {
				argsC = new Class[args.length];
				for (int i = 0; i < args.length; i++) {
					argsC[i] = args[i].getClass();
				}
			}
			//Object instance = null;
			Class<?> clase = instance.getClass();
			Method metodo = clase.getMethod(methodName, argsC);

			res = metodo.invoke(instance, args);
		} catch (Exception h) {

			Log.general.error(h.getMessage(), h);
			throw h;

		}
		return res;
	}
}