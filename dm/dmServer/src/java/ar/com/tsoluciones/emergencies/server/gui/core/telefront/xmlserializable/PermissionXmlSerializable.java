package ar.com.tsoluciones.emergencies.server.gui.core.telefront.xmlserializable;


import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import ar.com.tsoluciones.arcom.security.Permission;
import ar.com.tsoluciones.telefront.dispatcher.XmlSerializable;

/**
 * Created by IntelliJ IDEA.
 * User: fvaleriani
 * Date: 31/08/2005
 * Time: 09:53:20
 * To change this template use File | Settings | File Templates.
 */
public class PermissionXmlSerializable implements XmlSerializable {
    String xml;

    /**
     * Serializa un array de permisos
     *
     * @param permissionArray de permisos
     */
    public PermissionXmlSerializable(Permission[] permissionArray) {
        Document document = DocumentHelper.createDocument();
		Element rootElement = document.addElement("permissions");

		for (int i = 0; i < permissionArray.length; i++) {
		    Permission permission = permissionArray[i];

		    Element pieceElement = rootElement.addElement("piece");

		    pieceElement.addElement("id").setText(permission.getId().toString());
		    pieceElement.addElement("nombre").setText(permission.getName());
		    pieceElement.addElement("descripcion").setText(permission.getDescription());
		    pieceElement.addElement("borrado").setText((permission.isDeleted()) ? "Si" : " ");
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

