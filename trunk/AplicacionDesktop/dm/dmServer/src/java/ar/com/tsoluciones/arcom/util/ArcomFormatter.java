package ar.com.tsoluciones.arcom.util;

import java.util.*;
import java.text.*;
import java.lang.reflect.*;
import net.sf.jasperreports.engine.JRParameter;

/**
 * Implementa métodos para formatear datos y validar.
 *
 *
 */
public class ArcomFormatter {

	/**
	 * Formatea una fecha dada a un string
	 * @param date Fecha a formatear
	 * @param separador Separador a utilizar (ej: "/")
	 * @return Fecha formateada, por ejemplo: "10/12/2004"
	 */
	public static String formatDateAsString(Date date, String separador) {
		if (date == null) {
			return "";
		}
		if (separador == null) {
			separador = "";
		}
		SimpleDateFormat format = new SimpleDateFormat("dd" + separador + "MM" + separador + "yyyy");
		return format.format(date);
	}

	/**
	 * Formatea una fecha dada a un String, según la mascara.
	 * @param date Fecha a formatear.
	 * @param mascara Mascara válida, no null.
	 * @param locale El Locale, puede ser null.
	 * @return Fecha formateada, por ejemplo: "18/11/1977 16:23:12"
	 */
	public static String formatDateAsString(Date date, String mascara, Locale locale) {

		String fecha = "";
		if (mascara == null) {
			mascara = "";
		}
		if (locale == null) {
			locale = Locale.getDefault();
		}
		DateFormat formater = new SimpleDateFormat(mascara, locale);

		fecha = formater.format(date);
		return fecha;
	}

	public static boolean isInteger(String valor) {
		boolean valido = false;
		try {
			valor = valor.trim();
			Integer.parseInt(valor);
			valido = true;
		} catch (Exception y) {
			return false;
		}

		return valido;
	}

	public static boolean isLong(String valor) {
		boolean valido = false;
		try {
			valor = valor.trim();
			Long.parseLong(valor);
			valido = true;
		} catch (Exception y) {
			return false;
		}

		return valido;
	}

	public static boolean isDouble(String valor) {
		boolean valido = false;
		try {
			valor = valor.trim();
			Double.parseDouble(valor);
			valido = true;
		} catch (Exception y) {
			return false;
		}

		return valido;
	}

	public static boolean isBoolean01(String valor) {
		boolean valido = false;
		try {
			valor = valor.trim();
			if (valor.equals("0") || valor.equals("1")) {
				valido = true;
			}
		} catch (Exception y) {
			return false;
		}

		return valido;

	}

	public static boolean isBoolean(String valor) {
		boolean valido = false;
		try {
			valor = valor.trim();

			if (valor.equals("true") || valor.equals("false")) {
				valido = true;
			}
		} catch (Exception y) {
			return false;
		}

		return valido;

	}

	public static Date formatAsDate(String valor, String mascara) {
		//boolean valido = false;
		Date date = null;
		SimpleDateFormat format = null;
		try {
			valor = valor.trim();
			mascara = mascara.trim();
			format = new SimpleDateFormat(mascara);
			date = format.parse(valor);

		} catch (Exception y) {
			return null;
		}

		return date;

	}

	public static String getParametersValuesAsString(String[] values) {

		StringBuilder valor = new StringBuilder("");
		try {
			for (int i = 0; i < values.length; i++) {
				valor.append(values[i] + ",");
			}
			valor = valor.deleteCharAt(valor.length() - 1); //Saco el último ","
		} catch (Exception f) {
			return "-6";
		}

		return valor.toString();
	}

	public static Object getValueForJasper(String value, JRParameter param, String mascaraForDate) {

		Object objValue = null;
		Class<?>[] clase = { String.class };
		Object[] string = { value };
		try {
			Constructor<?> cons = null;
			if (param.getName().endsWith("multiple")) { //Son multiples ID Long
				cons = Long.class.getConstructor(clase);
			} else {
				cons = param.getValueClass().getConstructor(clase);
			}
			objValue = cons.newInstance(string);
		} catch (Exception y) {
			//No hago nada...
		}
		if (objValue == null || objValue instanceof java.util.Date) {
			return formatAsDate(value, mascaraForDate);
		}
		return objValue;
	}

	public static String getTipo(Class<?> clase) {

		String tipo = "Tipo de dato no especificado";
		String string = clase.getName();

		if (string.endsWith("String")) {
			tipo = "Texto";
		}
		if (string.endsWith("Long")) {
			tipo = "Numero entero";
		}
		if (string.endsWith("Integer")) {
			tipo = "Numero entero";
		}
		if (string.endsWith("Double")) {
			tipo = "Numero con decimales";
		}
		if (string.endsWith("Boolean")) {
			tipo = "true o false (V-F)";
		}
		if (string.endsWith("Date")) {
			tipo = "dd/MM/aaaa HH:mm:ss o dd/MM/aaaa";
		}
		if (string.endsWith("Byte")) {
			tipo = "0 1 2...8";
		}

		return tipo;
	}
}