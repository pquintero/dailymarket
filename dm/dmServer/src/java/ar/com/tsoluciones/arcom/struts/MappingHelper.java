package ar.com.tsoluciones.arcom.struts;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ar.com.tsoluciones.emergencies.server.gui.core.configuration.Configuration;

/**
 * CrimeReportMapper.java
 * Copyright (c) Telefónica Soluciones
 * Todos los derechos reservados.
 */

/**
 * <p>
 * Administra conversiones entre tipos de datos facilmente, abstrayendo detalles como la consideracion de valores
 * nulos.
 * </p>
 *
 * @author despada
 * @version 1.0, Oct 12, 2004, 10:36:04 AM
 * @see
 * @since 1.1
 */
public class MappingHelper {
	public static final String GMT = "GMT";
	// Parsing - Introduce values into the system

	/**
	 * Parsea una fecha en un string y lo devuelve como Date. Toma como formato esperado el
	 * estandar de este sistema.
	 * @param date Fecha a parsear
	 * @return Fecha parseada en un Date
	 */
	public static Date parseDate(String date) throws MappingException {
		return parseDate(date, null);
	}

	/**
	 * Parsea una fecha, tomando separadores alternativos
	 *
	 * @param date Fecha a parsear
	 * @param simpleDateFormat formato esperado para esta fecha
	 * @return Fecha parseada en un Date
	 * @throws MappingException cuando hay un problema con el mapeo
	 */
	public static Date parseDate(String date, SimpleDateFormat simpleDateFormat) throws MappingException {
		if (date == null || date.equals(""))
			return null;

		try {
			if (simpleDateFormat == null)
				simpleDateFormat = getDateFormat();

			return simpleDateFormat.parse(date);
		} catch (ParseException e) {
			throw new MappingException("Hubo un error fatal al parsear la fecha " + date + " a Date. Los validadores han fallado", e, date);
		}
	}

	/**
	 * Parsea una hora en un string y lo devuelve como Date.
	 * @param time Hora a parsear
	 * @return Fecha parseada en un Date
	 */
	public static Date parseTime(String time) throws MappingException {
		if (time == null || time.equals(""))
			return null;
		try {
			return getTimeFormat().parse(time);
		} catch (ParseException e) {
			throw new MappingException("Hubo un error fatal al parsear la hora " + time + " a Date. Los validadores han fallado", e, time);
		}
	}

	/**
	 * Parsea una hora en un string y lo devuelve como Date.
	 * @param time Hora a parsear
	 * @return Fecha parseada en un Date
	 */
	public static Date parseTimeComplete(String time) throws MappingException {
		if (time == null || time.equals(""))
			return null;
		try {
			return geTimetCompleteFormat().parse(time);
		} catch (ParseException e) {
			throw new MappingException("Hubo un error fatal al parsear la hora " + time + " a Date. Los validadores han fallado", e, time);
		}
	}

	/**
	 * Parsea una fecha y hora en un string y lo devuelve como Date.
	 * @param dateTime Fecha y Hora a parsear
	 * @return Fecha parseada en un Date
	 */
	public static Date parseDateTime(String dateTime) throws MappingException {
		if (dateTime == null || dateTime.equals(""))
			return null;
		try {
			return getDateTimeFormat().parse(dateTime);
		} catch (ParseException e) {
			throw new MappingException("Hubo un error fatal al parsear la fecha y hora " + dateTime + " a Date. Los validadores han fallado", e, dateTime);
		}
	}

	/**
	 * Obtiene el valor de un parámetro enviado en el request que es String. Lanza excepciones apropiadas si
	 * el valor no es del tipo esperado o no fue enviado
	 * @param value Valor a parsear
	 * @return el valor Long pedido
	 */
	public static String parseString(String value) throws MappingException {
		return parseString(value, true);
	}

	/**
	 * Obtiene el valor de un parámetro enviado en el request que es long. Lanza excepciones apropiadas si
	 * el valor no es del tipo esperado
	 * @param value Valor a parsear
	 * @param mandatory Si el parámetro es obligatorio. Cuando es true, entonces lanza una excepción si no fue recibido
	 * @return el valor Long pedido
	 */
	public static String parseString(String value, boolean mandatory) throws MappingException {
		if (value == null || value.trim().equalsIgnoreCase("")) {
			if (mandatory)
				throw new MappingException("El parámetro es obligatorio para la acción requerida", value);

			return null;
		}

		return value;
	}

	/**
	 * Castea en forma segura un string a un Integer
	 * @param value Un string que representa un Integer
	 * @return el valor Integer pedido
	 * @throws MappingException Lanzada cuando no se pudo realizar el mapping
	 */
	public static Integer parseInteger(String value) throws MappingException {
		return parseInteger(value, true);
	}

	/**
	 * Castea en forma segura un string a un Integer
	 * @param value Un string que representa un Integer
	 * @param mandatory true si el dato es obligatorio, false si no
	 * @return el valor Integer pedido
	 * @throws MappingException Lanzada cuando no se pudo realizar el mapping
	 */
	public static Integer parseInteger(String value, boolean mandatory) throws MappingException {
		value = parseString(value, mandatory);

		if (value == null || "".equals(value.trim()))
			return null;

		try {
			return Integer.valueOf(value);
		} catch (NumberFormatException e) {
			throw new MappingException("Se obtuvo el parámetro, pero el valor recibido '" + value + "' no pudo ser casteado a Integer", e, value);
		}
	}

	/**
	 * Obtiene el valor de un parámetro enviado en el request que es long. Lanza excepciones apropiadas si
	 * el valor no existe o no es del tipo esperado
	 * @param value Valor a parsear
	 * @return el valor Long pedido
	 */
	public static Long parseLong(String value) throws MappingException {
		return parseLong(value, true);
	}

	/**
	 * Obtiene el valor de un parámetro enviado en el request que es long. Lanza excepciones apropiadas si
	 * el valor no es del tipo esperado
	 * @param value Valor a parsear
	 * @param mandatory Si el parámetro es obligatorio. Cuando es true, entonces lanza una excepción si no fue recibido
	 * @return el valor Long pedido
	 */
	public static Long parseLong(String value, boolean mandatory) throws MappingException {
		// Obtener string
		value = parseString(value, mandatory);

		if (value == null)
			return null;

		try {
			return Long.valueOf(value);
		} catch (NumberFormatException e) {
			throw new MappingException("Se obtuvo el parámetro, pero el valor recibido '" + value + "' no pudo ser casteado a Long", e, value);
		}
	}

	/**
	 * Parsea un booleano HTML en un string. Notese que parseBoolean no sigue la convencion de mandatory
	 * que los otros metodos si siguen, ya que HTML envia "on" si un check esta activo o "" si no esta activo.
	 * @param value valor a parsear
	 * @return booleano traducido
	 * @throws MappingException si se envio en null value
	 */
	public static boolean parseBoolean(String value) throws MappingException {
		value = parseString(value, false);

		if (value == null)
			return false;

		return value.equalsIgnoreCase("on");

	}

	// Formatting - Export values from the system

	/**
	 * Formatea uns string para la view
	 * @param value El valor a formatear, si es null devuelve "" solo si mandatory es false
	 * @return El string formateado
	 */
	public static String formatString(String value) throws MappingException {
		return formatString(value, true);
	}

	/**
	 * Formatea uns string para la view
	 * @param value El valor a formatear, si es null devuelve "" solo si mandatory es false
	 * @param mandatory si true, entonces lanza una excepcion si value es null
	 * @return El string formateado
	 */
	public static String formatString(String value, boolean mandatory) throws MappingException {
		if (value == null && mandatory == false)
			return "";

		if (value == null && mandatory == true)
			throw new MappingException("El valor es nulo pero es obligatorio");

		return value;
	}

	/**
	 * Formatea uns Integer para la view
	 * @param value El valor a formatear, si es null devuelve "" solo si mandatory es false
	 * @return El Integer formateado
	 */
	public static String formatInteger(Integer value) throws MappingException {
		return formatInteger(value, true);
	}

	/**
	 * Formatea uns Integer para la view
	 * @param value El valor a formatear, si es null devuelve "" solo si mandatory es false
	 * @param mandatory si true, entonces lanza una excepcion si value es null
	 * @return El Integer formateado
	 */
	public static String formatInteger(Integer value, boolean mandatory) throws MappingException {
		if (value == null && mandatory == true)
			throw new MappingException("El valor es nulo pero es obligatorio");

		if (value == null)
			return "";

		return value.toString();
	}

	/**
	 * Formatea un Double para la view
	 * @param value El valor a formatear, si es null devuelve ""
	 * @return El Double formateado
	 */
	public static String formatDouble(Double value) {
		if (value != null)
			return Double.toString(value);
		else return "";
	}
	
	
	/**
	 * Formatea uns Long para la view
	 * @param value El valor a formatear, si es null devuelve "" solo si mandatory es false
	 * @return El Long formateado
	 */
	public static String formatLong(Long value) throws MappingException {
		return formatLong(value, true);
	}

	/**
	 * Formatea uns Long para la view
	 * @param value El valor a formatear, si es null devuelve "" solo si mandatory es false
	 * @param mandatory si true, entonces lanza una excepcion si value es null
	 * @return El Long formateado
	 */
	public static String formatLong(Long value, boolean mandatory) throws MappingException {
		if (value == null && mandatory == true)
			throw new MappingException("El valor es nulo pero es obligatorio");

		if (value == null)
			return "";

		return value.toString();
	}

	/**
	 * Formatea una fecha, pasandola a un string
	 * @param date fecha a formatear
	 * @return fecha formateada en un string
	 */
	public static String formatDate(Date date) {
		if (date == null)
			return null;

		return getDateFormat().format(date);
	}

	/**
	 * Formatea una hora, pasandola a un string
	 * @param time hora a formatear
	 * @return hora formateada en un string
	 */
	public static String formatTime(Date time) {
		if (time == null)
			return null;
		return getTimeFormat().format(time);
	}

	/**
	 * Formatea una hora, pasandola a un string, en formato completo.-
	 * @param time hora a formatear
	 * @return hora formateada en un string
	 */
	public static String formatTimetComplete(Date time) {
		if (time == null)
			return null;
		return geTimetCompleteFormat().format(time);
	}

	/**
	 * Toma un boolean y lo convierte a un String HTML
	 * @param value valor boolean
	 * @return String con el valor
	 */
	public static String formatBoolean(boolean value) {
		if (value)
			return "on";
		return "";
	}

	/**
	 * Formatea una fecha y hora, pasandola a un string
	 * @param dateTime fecha y hora a formatear
	 * @return fecha formateada en un string
	 */
	public static String formatDateTime(Date dateTime) {
		if (dateTime == null)
			return null;
		return getDateTimeFormat().format(dateTime);
	}

	/**
	 * Formatea una fecha y hora, pasandola a un string (dd/MM/yyyt HH:mm:ss)
	 * @param dateTime fecha y hora a formatear
	 * @return fecha formateada en un string
	 */
	public static String formatCompleteDateTime(Date dateTime) {
		if (dateTime == null)
			return null;
		return getCompleteDateTimeFormat().format(dateTime);
	}

	// Privates

	/**
	 * Obtiene un SimpleDateFormat configurado para el seteo de i18n adecuado
	 * @return Un SimpleDateFormat configurado
	 */
	private static SimpleDateFormat getDateFormat() {
		return new SimpleDateFormat("dd/MM/yyyy");
	}

	/**
	 * Obtiene un SimpleDateFormat configurado para obtener una hora
	 * @return Un SimpleDateFormat configurado
	 */
	private static SimpleDateFormat getTimeFormat() {
		return new SimpleDateFormat("HH:mm");
	}

	/**
	 * Obtiene un SimpleDateFormat configurado para obtener una hora
	 * @return Un SimpleDateFormat configurado
	 */
	private static SimpleDateFormat geTimetCompleteFormat() {
		return new SimpleDateFormat("HH:mm:ss");
	}

	/**
	 * Obtiene un SimpleDateFormat configurado para obtener una fecha y hora
	 * @return Un SimpleDateFormat configurado
	 */
	private static SimpleDateFormat getDateTimeFormat() {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm");
	}

	/**
	 * Obtiene un SimpleDateFormat configurado para obtener una fecha y hora, en formato completo.
	 * @return Un SimpleDateFormat configurado
	 */
	private static SimpleDateFormat getCompleteDateTimeFormat() {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}
	
	/**
	 * Utilizado para formatear con TZ.
	 * @param date
	 * @param offset
	 * @return String
	 */
	public static String formatDateTimeTZ(Date date, Integer offset) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fecha = simpleDateFormat.format(date);
        Integer timeZoneOffset = offset != null ? offset : Configuration.getInstance().getTimeZoneOffset();
        
        if (timeZoneOffset != null) {
        	if (timeZoneOffset > 0) {
        		fecha += String.format(" (%s+%s)", GMT, offset);  
        	} else if (offset < 0) {
        		fecha += String.format(" (%s%s)", GMT, offset);
        	} else {
    			fecha += String.format("(%s)", GMT);
        	}
        }
        return fecha;
	}

}
