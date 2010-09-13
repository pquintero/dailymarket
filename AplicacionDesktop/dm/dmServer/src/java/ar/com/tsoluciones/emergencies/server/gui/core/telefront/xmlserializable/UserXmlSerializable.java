package ar.com.tsoluciones.emergencies.server.gui.core.telefront.xmlserializable;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import ar.com.tsoluciones.arcom.security.User;
import ar.com.tsoluciones.telefront.dispatcher.XmlSerializable;

/**
 * Copyright (c) Telefónica Soluciones
 * Todos los derechos reservados.
 */

/**
 * <p>Serializador de User</p>
 *
 * @author despada
 * @version 1.0, May 5, 2005, 10:49:05 AM
 */
public class UserXmlSerializable implements XmlSerializable {
    String xml;

    /**
     * Serializa un array de user
     *
     * @param userArray Array de user
     */
    public UserXmlSerializable(User[] userArray) {
        Document document = DocumentHelper.createDocument();
		Element rootElement = document.addElement("users");

		for (int i = 0; i < userArray.length; i++) {
		    User user = userArray[i];

		    Element pieceElement = rootElement.addElement("piece");

		    pieceElement.addElement("id").setText(user.getId().toString());
		    pieceElement.addElement("username").setText(user.getUsername());
		    pieceElement.addElement("lastname").setText(user.getLastName() != null ? user.getLastName() : "");
		    pieceElement.addElement("firstname").setText(user.getFirstName() != null ? user.getFirstName() : "");
		    pieceElement.addElement("deleted").setText(""+user.isDeleted());
		}

		this.xml = document.asXML();
    }

    /**
     * Obtiene el string XML de las noticias
     *
     * @return El string XML de las noticias
     */
    public byte[] getBytes() {
        return xml.getBytes();
    }
}
