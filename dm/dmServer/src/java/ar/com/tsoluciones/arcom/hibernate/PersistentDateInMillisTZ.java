package ar.com.tsoluciones.arcom.hibernate;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;

/**
 * Tipo definido para persistir un <code>ar.com.tsoluciones.arcom.hibernate.DateInMillisTZ</code>
 */
public class PersistentDateInMillisTZ implements CompositeUserType {

	public Object assemble(Serializable cached, SessionImplementor session, Object owner) throws HibernateException {
		return cached;
	}

	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	public Serializable disassemble(Object value, SessionImplementor session) throws HibernateException {
		return (Serializable)value;
	}

	public boolean equals(Object object1, Object object2) throws HibernateException {
		if (object1 == object2)
            return true;
        if (object1 == null || object2 == null)
            return false;
		return object1.equals(object2);
	}

	public String[] getPropertyNames() {
		return new String[]{"timeInMillis", "offset"};
	}

	public Type[] getPropertyTypes() {
		return new Type[]{Hibernate.LONG, Hibernate.INTEGER};
	}

	public Object getPropertyValue(Object component, int idx) throws HibernateException {
		DateInMillisTZ dateInMillis = (DateInMillisTZ)component;
		if (idx == 0) {
			return dateInMillis.getTimeInMillis();
		} else 
			return dateInMillis.getOffset();
	}

	public int hashCode(Object object) throws HibernateException {
		return object.hashCode();
	}

	public boolean isMutable() {
		return false;
	}

	public Object nullSafeGet(ResultSet res, String[] names, SessionImplementor session, Object owner)
			throws HibernateException, SQLException {
		Long timeInMillis = (Long) Hibernate.LONG.nullSafeGet(res, names[0]);
        Integer offset = (Integer) Hibernate.INTEGER.nullSafeGet(res, names[1]);
        if (timeInMillis != null && offset != null) {
        	DateInMillisTZ one = new DateInMillisTZ();
        	one.setTimeInMillis(timeInMillis);
        	one.setOffset(offset);
        	return one;
        }
		return null;
	}

	public void nullSafeSet(PreparedStatement pstm, Object value, int idx, SessionImplementor arg3)
			throws HibernateException, SQLException {
		Logger.getLogger(PersistentDateInMillisTZ.class).debug("bindeando componente " + value);
        if (value == null) {
            pstm.setNull(idx, Hibernate.LONG.sqlType());
            pstm.setNull(idx+1, Hibernate.INTEGER.sqlType());
        } else {
        	DateInMillisTZ dateInMillis = (DateInMillisTZ)value;
            pstm.setLong(idx, dateInMillis.getTimeInMillis());
            pstm.setInt(idx+1, dateInMillis.getOffset());
        }		
	}

	public Object replace(Object original, Object target, SessionImplementor session, Object owner) throws HibernateException {
		return original;
	}

	public Class<DateInMillisTZ> returnedClass() {
		return DateInMillisTZ.class;
	}

	public void setPropertyValue(Object component, int idx, Object prop) throws HibernateException {
		throw new UnsupportedOperationException("DateInMillisTZ es inmutable");
	}



}
