/**
 * Copyright (c) Telefónica Soluciones
 * Todos los derechos reservados.
 */

package ar.com.tsoluciones.telefront.configuration;

import ar.com.tsoluciones.arcom.logging.Log;
import org.jconfig.ConfigurationManager;
import org.jconfig.event.FileListener;
import org.jconfig.event.FileListenerEvent;

/**
 * <p>Objeto configuracion de telefront</p>
 *
 * @author despada
 * @version 1.0, Dec 22, 2004, 10:59:34 AM
 * @since 1.1
 */
public class Configuration implements FileListener {
	private static Configuration instance;

	// Nombre del archivo xml de config
	private static final String CONFIG_FILENAME = "telefront";

	private boolean logResponsesInDebug;
	private boolean bandwithTesterEnabled;

	private boolean sessionCheck;
	private String variable;
	private String[] excludeArray;

	private String serializerImplementation;

	/**
	 * Obtiene la instancia del singleton
	 * @return Instancia del singleton
	 */
	public static synchronized Configuration getInstance() {
		return getInstance(false);
	}

	/**
	 * Obtiene la instancia del singleton
	 * @param reload Fuerza recarga de configuracion
	 * @return Instancia del singleton
	 */
	public static synchronized Configuration getInstance(boolean reload) {
		if (instance == null) {
			// Instanciar configuracion
			instance = new Configuration();
			// Escuchar cambios
			ConfigurationManager.getInstance().addFileListener(CONFIG_FILENAME, instance);
		}

		// Recargar si el usuario lo pidio
		if (reload)
			instance.reload();

		// Devolver objeto
		return instance;
	}

	/**
	 * Constructor del archivo de configuracion
	 */
	private Configuration() {
		reload();
	}

	/**
	 * Constructor privado del singleton
	 */
	private void reload() {
		Log.getLogger(this.getClass()).info("Cargando archivo de configuracion " + CONFIG_FILENAME + "_config.xml");

		// Levantar datos
		org.jconfig.Configuration configuration = ConfigurationManager.getConfiguration(CONFIG_FILENAME);

		// Tomar datos
		this.logResponsesInDebug = configuration.getBooleanProperty("responsesInDebug", false, "general");
		this.bandwithTesterEnabled = configuration.getBooleanProperty("bandwithTesterEnabled", false, "general");

		this.sessionCheck = configuration.getBooleanProperty("enabled", false, "session");
		this.variable = configuration.getProperty("variable", null, "session");
		this.excludeArray = configuration.getArray("exclude", new String[0], "session");

		this.serializerImplementation = configuration.getProperty("implementation", "ar.com.tsoluciones.telefront.serializer.javaserializer.JavaSerializer",
				"serializer");

		Log.getLogger(this.getClass()).info("Finalizo carga de archivo de configuracion");
	}

	/**
	 * Verifica si la llamada remota enviada debe ser excluida del control de session
	 * @param className Nombre de clase
	 * @param methodName Nombre del metodo
	 * @return true si debe ser, false si no
	 */
	public boolean isExcluded(String className, String methodName) {
		String string = className + "." + methodName;
		for (int i = 0; i < excludeArray.length; i++) {
			if (excludeArray[i].equalsIgnoreCase(string))
				return true;
		}

		return false;
	}

	/**
	 * Si se debe o no chequear la sesion
	 * @return true si se debe, false si no
	 */
	public boolean isSessionCheck() {
		return sessionCheck;
	}

	/**
	 * Obtiene la variable a chequear para sesion
	 * @return Variable en un string
	 */
	public String getVariable() {
		return variable;
	}

	/**
	 * Si deben logearse las respuestas en Debug o no
	 * @return true si se debe, false si no
	 */
	public boolean isLogResponsesInDebug() {
		return logResponsesInDebug;
	}

	/**
	 * Si el tester de ancho de banda esta encendido
	 * @return true si lo esta
	 */
	public boolean isBandwithTesterEnabled() {
		return bandwithTesterEnabled;
	}

	/**
	 * Obtiene la implementación de serializer requerida
	 * @return Implementación de serializer requerida
	 */
	public String getSerializerImplementation() {
		return serializerImplementation;
	}

	/**
	 * Escucha cambios en el archivo de configuracion y actualiza los valores de esta clase
	 * @param event Evento de cambio
	 */
	public void fileChanged(FileListenerEvent event) {
		Log.getLogger(this.getClass()).info("Alteración de configuración detectada");

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			Log.getLogger(this.getClass()).warn("Cuidado, espera interrumpida mientras se aguardaba para cargar el archivo");
		}

		// Recargar
		Configuration.getInstance(true);
	}
}