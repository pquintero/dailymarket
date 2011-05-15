package ar.com.tsoluciones.arcom.hibernate;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.hibernate.type.Type;

/**
 * Clase contenedora de parametros para queries de Hibernate.
 */
public class Parameters {

	private Map<String, ObjectType> values = new HashMap<String, ObjectType>();
 
	public void put(String key, Object value, Type type) {
		ObjectType ot = new ObjectType(value, type);
		this.values.put(key, ot);
	}

	public Type getType(String key){
		return values.get(key).getType();
	}

	public Object getValue(String key){
		return values.get(key).getValue();
	}

	public Set<String> keySet(){
		return values.keySet();
	}
	
	
	public static class ObjectType {
		
		private Object value;
		private Type type;

		public ObjectType(Object value, Type type) {
			super();
			this.value = value;
			this.type = type;
		}

		public Type getType() {
			return type;
		}

		public Object getValue() {
			return value;
		}
	}

	
}
