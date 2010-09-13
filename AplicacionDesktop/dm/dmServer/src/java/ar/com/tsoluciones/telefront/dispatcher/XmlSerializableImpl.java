package ar.com.tsoluciones.telefront.dispatcher;

/**
 * Copyright (c) Telefónica Soluciones
 * Todos los derechos reservados.
 */

/**
 * <p/>
 * Implementa en forma basica XmlSerializable, para que los services tengan un objeto basico que utilizar
 * </p>
 *
 * @author despada
 * @version 1.0, Mar 3, 2005, 11:25:58 AM
 * @see XmlSerializable
 * @since 1.0.1.0
 */
public class XmlSerializableImpl implements XmlSerializable {
    private String xmlString;

    /**
     * Construye un objeto XmlSerializableImpl
     *
     * @param xmlString String xml valido
     */
    public XmlSerializableImpl(String xmlString) {
        this.xmlString = xmlString;
    }

    /**
     * Obtiene el string xml guardado
     *
     * @return String xml guardado
     */
    public byte[] getBytes() {
        return xmlString.getBytes();
    }
}
