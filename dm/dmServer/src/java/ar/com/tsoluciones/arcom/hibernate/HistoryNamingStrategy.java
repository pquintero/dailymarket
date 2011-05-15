package ar.com.tsoluciones.arcom.hibernate;

import org.hibernate.cfg.NamingStrategy;

/**
 *
 *
 * <p>Company: Tito Company.</p>
 *
 * @author Ariel Clocchiatti
 * @version 1.0
 */
public class HistoryNamingStrategy extends CustomNamingStrategy {

	//Deberia ser reemplazado por un prefijo customizable por XML o archivo de configuracion
	private static String SUFIJO = "_history";

	public static final NamingStrategy INSTANCE = new HistoryNamingStrategy();

	/**
	 * Return the unqualified class name, mixed case converted to
	 * underscores
	 */
	@Override
	public String classToTableName(String className) {
		return super.classToTableName(className)+SUFIJO;
	}

	/**
	 * Convert mixed case to underscores
	 */
	@Override
	public String tableName(String tableName) {
		return super.tableName(tableName)+SUFIJO;
	}
}
