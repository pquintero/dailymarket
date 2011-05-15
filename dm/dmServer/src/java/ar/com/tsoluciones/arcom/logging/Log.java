package ar.com.tsoluciones.arcom.logging;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

/**
 * Copyright (c) Telefónica Soluciones Todos los derechos reservados.
 * <p>
 * Provee estaticamente categorias standard de logeo, en 5 niveles. Se configura
 * via un archivo de configuracion, dependiente de la implementacion.
 * Actualmente la implementacion es LOG4J
 * </p>
 * <p/>
 * <p>
 * Para logear: <code>
 * Log.categoria.debug("msg....");
 * o
 * Log.categoria.debug("msg....", exception);
 * </code>
 * </p>
 *
 * @author Andres Herrera - aherrera@artech-consulting.com
 * @version 1.0 - 10/03/2004
 * @see ar.com.tsoluciones.arcom.logging.TSLogger TSLogger>
 * @since 0.1
 */

public class Log {
	// Categorías de logging
	public static final TSLogger xml;
	public static final TSLogger general;
	public static final TSLogger security;
	public static final TSLogger util;

	// ///////// ARCOM V2.0 ///////////
	public static final TSLogger osWorkflow;
	public static final TSLogger osApprovalRule;
	public static final TSLogger hibernate;

	// Levanta la configuración del archivo correspondiente, y define las
	// categorias
	static {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream is = loader.getResourceAsStream("log4j.properties");
		if (is == null) {
			System.out.println("LOG4J properties file not found in the classpath");
		} else {
			try {
				System.out.println("LOG4J properties file found " + loader.getResource("log4j.properties").toString());
				Properties props = new Properties();
				try {
					props.load(is);
				} catch (IOException ioe) {
					System.err.println("ERROR READING PROPERTIES FILE : "
							+ loader.getResource("log4j.properties").toString());
				}
				PropertyConfigurator.configure(props);
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					System.err.println("Error closing file " + e.getMessage());
				}
			}
		}

		xml = TSLogger.getLogger("tsoluciones.xml");
		general = TSLogger.getLogger("tsoluciones.general");
		security = TSLogger.getLogger("tsoluciones.security");
		util = TSLogger.getLogger("tsoluciones.util");

		// ///////// ARCOM V2.0 ///////////
		osWorkflow = TSLogger.getLogger("tsoluciones.osWorkflow");
		osApprovalRule = TSLogger.getLogger("tsoluciones.osApprovalRule");
		hibernate = TSLogger.getLogger("tsoluciones.hibernate");
	}

	/**
	 * Obtiene un logger que puede configurarse en log4J.properties como [ruta
	 * completa a la clase] (por ej, ar.com.tsoluciones.arcom.security.User);
	 *
	 * @param loggerClass
	 *            Clase que ejecuta el logeo
	 * @return Objeto Logger
	 */
	public static TSLogger getLogger(Class<?> loggerClass) {
		return TSLogger.getLogger(loggerClass.getName());
	}
}