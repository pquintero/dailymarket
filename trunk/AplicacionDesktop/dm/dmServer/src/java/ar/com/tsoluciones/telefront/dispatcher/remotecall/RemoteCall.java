package ar.com.tsoluciones.telefront.dispatcher.remotecall;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import ar.com.tsoluciones.util.Cast;

/**
 * Copyright (c) Telefónica Soluciones
 * Todos los derechos reservados.
 */

/**
 * <p>
 * Representa logicamente una llamada a procedimiento remoto enviada por
 * Telefront JScript
 * </p>
 *
 * @author despada
 * @version 1.0, May 4, 2005, 11:56:52 AM
 */
public class RemoteCall {
	private String className;
	private String methodName;
	private Parameter[] parameterArray;

	/**
	 * Construye una representacion logica de la llamada remota
	 * @param document El XML llegado desde el cliente
	 */
	public RemoteCall(Document document) {
		// Clase
		Node classNameNode = document.selectSingleNode("/execute/className");
		assert classNameNode != null : "No se encontró el tag className";
		this.className = classNameNode.getText();

		// Metodo
		Node methodNameNode = document.selectSingleNode("/execute/methodName");
		assert methodNameNode != null : "No se encontró el tag methodName";
		this.methodName = methodNameNode.getText();

		// Parametros (puede no haber)
		Element[] elementArray = Cast.castList(Element.class,
						document.getRootElement().element("parameters").elements("parameter")).toArray(new Element[0]);

		this.parameterArray = new Parameter[elementArray.length];
		for (int i = 0; i < elementArray.length; i++)			
			this.parameterArray[i] = ParameterFactory.newInstance(elementArray[i]);
	}

	/**
	 * El nombre de la clase a llamar
	 * @return El nombre de la clase a llamar
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * El nombre del metodo a llamar
	 *
	 * @return El nombre del metodo a llamar
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * La lista de parametros
	 *
	 * @return La lista de parametros
	 */
	public Parameter[] getParameterArray() {
		return parameterArray;
	}

	public static void main(String args[]) throws Throwable {
		String xml =
				"<execute>" +
				"    <className>ar.com.tsoluciones.emergencies.server.gui.core.telefront.action.LoginManagerService</className>" +
				"    <methodName>login</methodName>" +
				"    <parameters>" +
				"        <parameter type=\"simple\">operador01</parameter>" +
				"        <parameter type=\"simple\">operador01</parameter>" +
				"        <parameter type=\"simple\">1</parameter>" +
                "        <parameter type=\"collection\" class=\"ar.com.tsoluciones.telefront.dispatcher.remotecall.Basico\">" +
                "            <basico><campo>algo</campo><valor>valor</valor></basico>" +
                "            <basico><campo>algoritmo</campo><valor>valoritmo</valor></basico>" +
                "        </parameter>" +
				"    </parameters>" +
				"</execute>";

		Document doc = DocumentHelper.parseText(xml);
        new RemoteCall(doc);
	}
}
