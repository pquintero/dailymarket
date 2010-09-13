package ar.com.tsoluciones.telefront.dispatcher;

/**
 * Copyright (c) Telefónica Soluciones
 * Todos los derechos reservados.
 */

/**
 * <p>
 * Si se devuelve un objeto desde un service que implementa esta interfaz, Dispatcher no lo serializa, sino que toma
 * el XML mediante la interfaz y lo envia. Puede implementar esta interfaz para construir serializadores optimizados de objetos.
 * </p>
 *
 * @author despada
 * @version 1.0, Mar 3, 2005, 11:27:23 AM
 * @see XmlSerializableImpl
 * @since 1.0.1.0
 */
public interface XmlSerializable
{
	/**
	 * Obtiene el string xml de este objeto
	 * @return XML de este objeto
	 */
	//public String getXmlString();
    public byte[]  getBytes();
}
