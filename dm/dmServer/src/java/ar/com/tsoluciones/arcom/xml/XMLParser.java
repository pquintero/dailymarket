package ar.com.tsoluciones.arcom.xml;

import ar.com.tsoluciones.arcom.logging.Log;
import org.w3c.dom.*;
import org.xml.sax.EntityResolver;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * <p> Esta clase permite parsear el contenido XML de un InputStream
 * convirtiendolo en un arbol DOM y realizar distintas consultas
 * sobre sus nodos.
 * </p>
 * <p> Ejemplo de uso:
 * <pre>
 * 		XMLParser parser = new XMLParser();
 * 		if (parser.parse(myInputStream))
 * 		{
 *    	parser.getNodeValue("cxml.header.from.name");
 *      ...
 *    }
 *  </pre>
 * </p>
 *
 * @author Andres Herrera &lt;aherrera@artech-consulting.com&gt;
 * @version 1.0, 22/01/2004
 * @since 0.1 tsoluciones
 */
public class XMLParser {
	public static final EntityResolver CLASS_LOADER_RESOLVER = new ClassLoaderEntityResolver();

	private Document doc;
	private Element root;
	private boolean validXML;
	private String baseURL;
	private EntityResolver entityResolver;

	/**
	 * <p> Constructor</p>
	 *
	 * @since 0.1 tsoluciones
	 */
	public XMLParser() {
		super();
		doc = null;
		root = null;
		validXML = false;
		//setBaseURL(Thread.currentThread().getContextClassLoader().getResource(".").toString());
		setBaseURL("/");
		Log.xml.debug("XMLParser instantiated");
	}

	public EntityResolver getEntityResolver() {
		return entityResolver;
	}

	public void setEntityResolver(EntityResolver entityResolver) {
		this.entityResolver = entityResolver;
	}

	public String getBaseURL() {
		return baseURL;
	}

	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}

	/**
	 * <p>Metodo principal de esta clase. Debe pasarse un InputStream conteniendo
	 * un documento XML antes de poder realizar cualquier otra consulta.</p>
	 *
	 * @param is el InputStream que contiene el documento XML
	 * @return true si el parsing fue exitoso, false en otro caso
	 * @since 0.1 tsoluciones
	 */
	public boolean parse(InputStream is) {
		Log.xml.debug("InputStream parsing requested");
		try {
			doc = getDocument(is);
			validXML = doc != null;
		} catch (Throwable t) {
			Log.xml.warn("Exception parsing: " + is.toString(), t);
			validXML = false;
		}

		if (validXML)
			root = doc.getDocumentElement();

		return validXML;
	}

	/**
	 * <p>Indica si se ha ejecutado exitosamente el parsing del InputStream</p>
	 *
	 * @return true si el parsing fue exitoso, false en otro caso
	 * @since 0.1 tsoluciones
	 */
	public boolean isValidXML() {
		return validXML;
	}

	/**
	 * <p>Retorna el nombre del nodo principal del XML, una vez parseado el documento</p>
	 *
	 * @return el nombre del nodo principal del XML
	 * @since 0.1 tsoluciones
	 */
	public String getRootName() {
		if (validXML)
			return root.getNodeName();
		return null;
	}

	public Node getRootNode() {
		if (validXML)
			return root;
		return null;
	}

	/**
	 * <p>Retorna el valor del atributo dado, para el primer nodo encontrado indicado por el path</p>
	 *
	 * @param nodePath la ruta al nodo, por ejemplo: cxml.header.from.name
	 * @param nodePath el atributo, por ejemplo: nodeId
	 * @return el valor del atributo del nodo dado, null si no lo encuentra o si no encuentr al nodo
	 * @since 0.1 tsoluciones
	 */
	public String getNodeAttribute(String nodePath, String attribute) {
		Node node;
		if (!validXML)
			return null;
		node = getDottedNode(nodePath);
		if (node != null)
			return getAttribute(node, attribute);

		Log.xml.debug("No match for: " + nodePath);
		return null;
	}

	/**
	 * <p>Retorna el texto asociado con la primer ocurrencia del nodo dado</p>
	 *
	 * @param nodePath la ruta al nodo, por ejemplo: cxml.header.from.name
	 * @return el valor/text asociado con el nodo, null si no lo encuentra
	 * @since 0.1 tsoluciones
	 */
	public String getNodeValue(String nodePath) {
		Node node, child;

		if (!validXML)
			return null;
		node = getDottedNode(nodePath);
		if (node != null) {
			child = node.getFirstChild();
			if (child != null) {
				if (child.getNodeType() == Node.TEXT_NODE)
					return child.getNodeValue();

				Log.xml.debug("No text child for: " + nodePath);
				return null;
			}

			Log.xml.debug("No child for: " + nodePath);
			return null;
		}
		Log.xml.debug("No match for: " + nodePath);
		return null;
	}

	public Node[] getNodes(String nodePath) {
		if (!validXML)
			return new Node[0];

		return getDottedNodes(nodePath, new Node[] { root });
	}

	public Node[] getNodes(Node node, String nodePath) {
		if (!validXML)
			return new Node[0];

		return getDottedNodes(nodePath, new Node[] { node });

	}

	public Node getNode(String nodePath) {
		return getNode(root, nodePath);
	}

	public Node getNode(Node node, String nodePath) {
		if (!validXML)
			return null;

		return getDottedNode(node, nodePath);
	}

	/**
	 * <p>Retorna el valor del atributo dado, para el objeto Node indicado</p>
	 *
	 * @param node      el nodo del cual se quiere obtener el atributo
	 * @param attribute el atributo que se quiere obtener
	 * @return el valor del atributo, null si no lo encuentra
	 * @since 0.1 tsoluciones
	 */
	protected String getAttribute(Node node, String attribute) {

		if (!validXML || node == null || attribute == null || attribute.trim().equals(""))
			return null;

		if (node.hasAttributes()) {
			NamedNodeMap attributesMap = node.getAttributes();
			Node attrNode = attributesMap.getNamedItem(attribute);
			if (attrNode == null) {
				Log.xml.debug("Attribute: " + attribute + " not found in " + node.getNodeName());
				return null;
			}

			if (attrNode.getNodeType() == Node.ATTRIBUTE_NODE)
				return attrNode.getNodeValue();

			Log.xml.debug(attribute + " is not an attribute of " + node.getNodeName());
			return null;

		}

		Log.xml.debug("Node: " + node.getNodeName() + " has no attributes");
		return null;
	}

	/**
	 * <p>Retorna el primer objeto Node indicado por el nodePath</p>
	 *
	 * @param nodePath el path hacia el nodo que se quiere obtener
	 * @return el primer objeto Node asociado a ese path, null si no lo encuentra
	 * @since 0.1 tsoluciones
	 */
	protected Node getDottedNode(String nodePath) {
		return getDottedNode(root, nodePath);
	}

	/**
	 * <p>Retorna el primer objeto Node indicado por el nodePath</p>
	 *
	 * @param nodePath el path hacia el nodo que se quiere obtener
	 * @return el primer objeto Node asociado a ese path, null si no lo encuentra
	 * @since 0.1 tsoluciones
	 */
	protected Node getDottedNode(Node node, String nodePath) {
		Node[] nodes = null;
		int index;
		String path;

		// Elimina espacio en blanco (pre y post)
		nodePath = nodePath.trim();
		// Valida que se parseo bien el XML, y que lo que recibe no es blanco, vacio o nulo
		if (!validXML || nodePath == null || nodePath.equals(""))
			return null;

		index = nodePath.indexOf('.');

		// si no hay '.', usa la longitud como indice para obtener el substring
		if (index == -1)
			index = nodePath.length();

		//obtiene el substring hasta el primer punto o el final del string
		path = nodePath.substring(0, index);

		if (path.equalsIgnoreCase(getRootName())) {
			if (index < (nodePath.length() - 1)) {
				nodes = getDottedNodes(nodePath.substring(index + 1, nodePath.length()), new Node[] { node });
				return nodes[0];

			}
			return root;
		}

		nodes = getDottedNodes(nodePath, new Node[] { node });
		return nodes[0];
	}

	/**
	 * <p>Retorna el primer objeto Document obtenido luego de realizar el parseo</p>
	 * @param is
	 * @return
	 * @since 0.1 tsoluciones
	 */
	private Document getDocument(InputStream is) {
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			docBuilder.setEntityResolver(entityResolver);
			if (is.available() <= 0) {
				return null;
			}
			doc = docBuilder.parse(is, baseURL);
			doc.getDocumentElement().normalize();
			return doc;

			//Error handling debe ser mejorado, ver por ejemplo http://www.eleceng.ohio-state.edu/~khan/khan/Presentations/Taiwan/PDFfiles/XMLproramming.pdf
		} catch (SAXParseException err) {
			Log.xml.error("SAX parsing error, at line: " + err.getLineNumber(), err);
			return null;
		} catch (SAXException e) {
			Log.xml.error("SAXException", e);
			return null;
		} catch (ParserConfigurationException pce) {
			Log.xml.error("ParserConfigurationException: " + pce.toString(), pce);
			return null;
		} catch (IOException ioe) {
			Log.xml.error("IOException while parsing. " + ioe);
			return null;
		} catch (Throwable t) {
			Log.xml.error("Unexpected exceptiont. ", t);
			return null;
		}
	}

	private Node[] getDottedNodes(String nodePath, Node[] fromNodes) {
		int index;
		ArrayList<Node> list = new ArrayList<Node>();
		NodeList childs;
		String path;

		// Elimina espacio en blanco (pre y post)
		nodePath = nodePath.trim();
		// Valida que se parseo bien el XML, y que lo que recibe no es blanco, vacio o nulo
		if (!validXML || nodePath == null || nodePath.equals(""))
			return null;

		index = nodePath.indexOf('.');

		// si no hay '.', uso la longitud como indice para obtener el substring
		if (index == -1)
			index = nodePath.length();

		path = nodePath.substring(0, index);
		// for all the nodes received...
		for (int j = 0; j < fromNodes.length; j++) {
			if (fromNodes[j].hasChildNodes()) {
				childs = fromNodes[j].getChildNodes();

				//Add to the list all those childs that match the path...
				for (int i = 0; i < childs.getLength(); i++) {
					if (path.equalsIgnoreCase(childs.item(i).getNodeName())) {
						list.add(childs.item(i));
					}
				} //end for

			} // if it has no childs... we do nothing, just keep looping
		} // end for...
		if (!list.isEmpty()) {
			Node[] nodes = list.toArray(new Node[] {});
			if (index < (nodePath.length() - 1))
				return getDottedNodes(nodePath.substring(index + 1, nodePath.length()), nodes);

			return nodes;
		}

		//no child matched the path...
		Log.xml.debug("No child matched path: " + path);
		return new Node[0];
	}

	public static String getNodeAttribute(Node node, String attribute) {
		if (node != null) {
			if (node.hasAttributes()) {
				NamedNodeMap attributesMap = node.getAttributes();
				Node attrNode = attributesMap.getNamedItem(attribute);
				if (attrNode == null) {
					Log.xml.debug("Attribute: " + attribute + " not found in " + node.getNodeName());
					return null;
				}
				if (attrNode.getNodeType() == Node.ATTRIBUTE_NODE)
					return attrNode.getNodeValue();

				Log.xml.debug(attribute + " is not an attribute of " + node.getNodeName());
				return null;
			}
			Log.xml.debug("Node: " + node.getNodeName() + " has no attributes");
			return null;

		}

		Log.xml.debug("No match for: node");
		return null;

	}

	public static String getNodeValue(Node node) {
		Node child;

		if (node != null) {
			if (node.getNodeType() == Node.TEXT_NODE)
				return node.getNodeValue();

			child = node.getFirstChild();
			if (child != null) {
				if (child.getNodeType() == Node.TEXT_NODE)
					return child.getNodeValue();

				Log.xml.debug("No text child for: " + node.getNodeName());
				return null;

			}

			Log.xml.debug("No child for: " + node.getNodeName());
			return null;

		}

		Log.xml.debug("No match for: node");
		return null;

	}
}