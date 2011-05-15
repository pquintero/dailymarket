package ar.com.tsoluciones.arcom.hibernate;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.postgresql.util.PGobject;

/**
 * Clase que representa una fila o registro en respuesta a una consulta hecha con alguna
 * de las APIs de Hibernate. Se usa en conjunción con {@code ar.com.tsoluciones.arcom.hibernate.AliasToResultRowTransformer}.
 * @see ar.com.tsoluciones.arcom.hibernate.AliasToResultRowTransformer
 */
public class ResultRow {

	private Map<String, Object> map;

	public ResultRow(Object[] tuple, String[] aliases) {
		map = new HashMap<String, Object>(tuple.length);
		for (int i = 0; i < tuple.length; i++) {
			String alias = aliases[i];
			if (alias != null)
				map.put(alias, tuple[i]);
		}
	}

	public Long getLong(String nombre) {
		return getLong(nombre, false);
	}

	public Long getLong(String nombre, boolean notNull) {
		Number number = (Number) map.get(nombre);
		if (number != null)
			return number.longValue();

		return notNull ? Long.valueOf(0) : null;
	}

	public Integer getInteger(String nombre) {
		return getInteger(nombre, false);
	}

	public Integer getInteger(String nombre, boolean notNull) {
		Number number = (Number) map.get(nombre);
		if (number != null)
			return number.intValue();

		return notNull ? Integer.valueOf(0) : null;
	}

	public String getString(String nombre) {
		Object obj = map.get(nombre);
		if (obj instanceof Clob) {
			Clob clob = (Clob) obj;
			try {
				return clob.getSubString(1, (int) clob.length());
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}

		return obj == null ? null : obj.toString();
	}

	public Date getDate(String nombre) {
		Date date = (Date) map.get(nombre);
		return date != null ? new Date(date.getTime()) : null;
	}

	public byte[] getBytes(String nombre) {
		Object obj = map.get(nombre);
		if (obj instanceof byte[]) {
			return (byte[]) obj;
		} else if (obj instanceof Blob) {
			try {
				Blob blob = (Blob) obj;
				return blob.getBytes(1, (int) blob.length());
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		} else {
			throw new RuntimeException("Tipo de campo inválido: " + obj.getClass().getName());
		}
	}

	public Double getDouble(String nombre) {
		return getDouble(nombre, false);
	}

	public Double getDouble(String nombre, boolean notNull) {
		Number number = (Number) map.get(nombre);
		if (number != null)
			return number.doubleValue();

		return notNull ? new Double(0) : null;
	}

	public PGobject getPGobject(String nombre) {
		PGobject pgo = (PGobject) map.get(nombre);
		return pgo;
	}

	public Float getFloat(String nombre) {
		return getFloat(nombre, false);
	}

	public Float getFloat(String nombre, boolean notNull) {
		Number number = (Number) map.get(nombre);
		if (number != null)
			return number.floatValue();

		return notNull ? new Float(0) : null;
	}
	
	public Boolean getBoolean(String nombre){
		if(map.get(nombre) instanceof Boolean)
			return (Boolean)map.get(nombre);
		return null;
	}	
}
