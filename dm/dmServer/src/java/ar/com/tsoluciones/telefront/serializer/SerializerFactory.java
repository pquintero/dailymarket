package ar.com.tsoluciones.telefront.serializer;

import ar.com.tsoluciones.telefront.configuration.Configuration;

/**
 * <p>Construye un serializer XML dado por el archivo de configuracion</p>
 *
 * @author despada
 * @version 1.0, Jul 06, 2005, 17:46:35 PM
 */
public class SerializerFactory
{
	/**
	 * Construye el serializer apropiado
	 * @return Serializer apropiado
	 */
	public static Serializer newInstance()
	{
		String serializerImplementation = Configuration.getInstance().getSerializerImplementation();

		try {
			// Mejora de performance, solo instanciar una vez por reflection, tener en cuenta que el archivo de config puede cambiar
			Class<?> serializerImplementationClass = Class.forName(serializerImplementation);
			return (Serializer) serializerImplementationClass.newInstance();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("La implementación de serializador explicitada no existe: " + serializerImplementation, e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Error de seguridad al lacceder a la implementacion", e);
		} catch (InstantiationException e) {
			throw new RuntimeException("Error al intentar instanciar el serializer", e);
		}
	}
}
