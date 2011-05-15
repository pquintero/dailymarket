package ar.com.tsoluciones.arcom.hibernate;

import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.NamingStrategy;

/**
 * Realiza el Script de Update de la BBDD  F911.
 */
public class SchemaUpdate {

	public static void main(String[] args) {
		// Usar de esta manera para que imprima el script por pantalla SIN ejecutar los cambios
        new SchemaUpdate().doScript();
	}

	/**
	 * Chequea las diferencias del mapeo contra la estructura de la base de datos
	 * e imprime el script con la diferencia.
	 */
	public void doScript() {
		execute(true);
	}

	/**
	 * Ejecuta los chequeos de los mapeos contra la estructura de la base de datos
	 * @param script Determina si debe o no imprimir por la salida estandard el script con
	 * 		  		 las diferencias.
	 */
	@SuppressWarnings("unchecked")
	private void execute(boolean script) {
	    executeUpdate("hibernate.cfg.xml", CustomNamingStrategy.INSTANCE, script);
	}
	
	private static void executeUpdate(String configFile, NamingStrategy strategy, boolean script) {
		Configuration cfg = new Configuration();
        if (strategy != null)
        	cfg.setNamingStrategy(strategy);
		cfg = cfg.configure(Thread.currentThread().getContextClassLoader().getResource(configFile));
        System.out.println("Update para " + cfg.getProperties().get("hibernate.connection.url"));
        org.hibernate.tool.hbm2ddl.SchemaUpdate object = new org.hibernate.tool.hbm2ddl.SchemaUpdate(cfg);
		object.execute(script, false);		
	}
}