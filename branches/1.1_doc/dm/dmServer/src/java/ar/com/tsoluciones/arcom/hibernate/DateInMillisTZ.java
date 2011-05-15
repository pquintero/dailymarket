package ar.com.tsoluciones.arcom.hibernate;

import java.io.Serializable;
import java.util.Date;
import java.util.TimeZone;

/**
 * Representa una fecha guardando su representación en milisegundos y el offset respecto del horario de referencia GMT.
 */
public class DateInMillisTZ implements Serializable {
	private static final int HORA_IN_MILLIS = 3600000;
	private Long timeInMillis;
	private Integer offset;

	/**
	 * Constructor para uso de Hibernate 
	 */
	public DateInMillisTZ() {
	}
	
	/**
	 * Constructor usado para generar el objeto a partir de un java.util.Date + el time zone utilizado.
	 * @return
	 */
	public DateInMillisTZ(Date date, TimeZone tz) {
		if (date != null) {
			this.timeInMillis = date.getTime();
			this.offset = (int)(tz.getRawOffset() / HORA_IN_MILLIS);  
		}
			
	}
	public Long getTimeInMillis() {
		return timeInMillis;
	}
	public void setTimeInMillis(Long timeInMillis) {
		this.timeInMillis = timeInMillis;
	}
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((offset == null) ? 0 : offset.hashCode());
		result = prime * result + ((timeInMillis == null) ? 0 : timeInMillis.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final DateInMillisTZ other = (DateInMillisTZ) obj;
		if (offset == null) {
			if (other.offset != null)
				return false;
		} else if (!offset.equals(other.offset))
			return false;
		if (timeInMillis == null) {
			if (other.timeInMillis != null)
				return false;
		} else if (!timeInMillis.equals(other.timeInMillis))
			return false;
		return true;
	}
}
