package ar.com.tsoluciones.telefront.serializer;

/**
 * <p>Funcionalidad a implementar por un serializador XML</p>
 *
 * @author despada
 * @version 1.0, Jul 06, 2005, 17:46:35 PM
 */
public interface Serializer
{
	/**
	 * Serializa un objeto a XML
	 * @param object Objeto a serializar
	 * @return Objeto serializado en un XML
	 */
	String serialize(Object object);
}