package ar.com.tsoluciones.telefront.serializer.xstreamserializer;

import ar.com.tsoluciones.telefront.serializer.Serializer;
import com.thoughtworks.xstream.XStream;

/**
 * <p>
 * Serializador que emplea XStream para serializar los entities
 * </p>
 *
 * @author despada
 * @version 1.0, Jul 06, 2005, 17:46:35 PM
 */
public class XStreamSerializer implements Serializer
{
	/**
	 * Serializa el objeto a XML
	 * @param object Objeto a serializar
	 * @return Objeto serializado
	 */
	public String serialize(Object object)
	{
		XStream xstream = new XStream();
    return xstream.toXML(object);
	}
}
