package ar.com.tsoluciones.emergencies.server.gui.core.telefront.xmlserializable;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import ar.com.tsoluciones.arcom.security.Role;
import ar.com.tsoluciones.telefront.dispatcher.XmlSerializable;

/**
 * Created by IntelliJ IDEA.
 * User: fvaleriani
 * Date: 31/08/2005
 * Time: 09:53:20
 * To change this template use File | Settings | File Templates.
 */
public class RoleXmlSerializable implements XmlSerializable {
	String xml;

	/**
	 * Serializa un array de roles
	 *
	 * @param roleArray de roles
	 */
	public RoleXmlSerializable(Role[] roleArray) {
		Document document = DocumentHelper.createDocument();
		Element rootElement = document.addElement("roles");

		for (int i = 0; i < roleArray.length; i++) {
			Role role = roleArray[i];

			Element pieceElement = rootElement.addElement("piece");

			pieceElement.addElement("id").setText(role.getId().toString());
			pieceElement.addElement("nombre").setText(role.getName());
			pieceElement.addElement("descripcion").setText(role.getDescription());
		}

		this.xml = document.asXML();
	}

	/**
	 * Obtiene el string XML de las noticias
	 *
	 * @return El string XML de las noticias
	 */
	public byte[] getBytes() {
		return this.xml.getBytes();
	}
}