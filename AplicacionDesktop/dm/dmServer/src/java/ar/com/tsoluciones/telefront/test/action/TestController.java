package ar.com.tsoluciones.telefront.test.action;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import ar.com.tsoluciones.arcom.cor.ServiceException;
import ar.com.tsoluciones.telefront.dispatcher.XmlSerializable;
import ar.com.tsoluciones.telefront.dispatcher.XmlSerializableImpl;
import ar.com.tsoluciones.telefront.servicefactory.TelefrontServiceFactory;
import ar.com.tsoluciones.telefront.test.dto.Form;

/**
 * Copyright (c) Telefónica Soluciones
 * Todos los derechos reservados.
 */

/**
 * <p>Coordina la pagina test.jsp presentandole metodos telefront</p>
 *
 * @author despada
 * @version 1.0, May 11, 2005, 10:02:51 AM
 */
public class TestController extends TelefrontServiceFactory
{
	/**
	 * Genera una instancia del Controller
	 * @return instancia del Controller
	 */
	@Override
	public Object newInstance()
	{
		return new TestController();
	}

	/**
	 * Devuelve un string "Hello World"
	 * @return "Hello World"
	 */
	public String getHelloWorld()
	{
		return "Hello World (ñ)";
	}

	/**
	 * Genera un XML para los tests
	 * @return Un objeto XmlSerializable
	 */
	public XmlSerializable getTestXml()
	{
		try
		{
    	Document document = DocumentHelper.parseText("<companies></companies>");
			Element rootElement = document.getRootElement();

			Element companyElement;

			companyElement = rootElement.addElement("company");
			companyElement.addElement("ticker").setText("TEMP");
			companyElement.addElement("name").setText("Telefonica Empresas Argentina");
			companyElement.addElement("share").setText("10%");
			companyElement.addElement("employees").setText("800");
			companyElement.addElement("revenues").setText("112911");

			companyElement = rootElement.addElement("company");
			companyElement.addElement("ticker").setText("NEC");
			companyElement.addElement("name").setText("NEC Argentina");
			companyElement.addElement("share").setText("1%");
			companyElement.addElement("employees").setText("30");
			companyElement.addElement("revenues").setText("1111");

			companyElement = rootElement.addElement("company");
			companyElement.addElement("ticker").setText("MSFT");
			companyElement.addElement("name").setText("Microsoft Argentina");
			companyElement.addElement("share").setText("25%");
			companyElement.addElement("employees").setText("1200");
			companyElement.addElement("revenues").setText("12919121");

			companyElement = rootElement.addElement("company");
			companyElement.addElement("ticker").setText("EDS");
			companyElement.addElement("name").setText("EDS Argentina");
			companyElement.addElement("share").setText("20%");
			companyElement.addElement("employees").setText("1500");
			companyElement.addElement("revenues").setText("199932");

			companyElement = rootElement.addElement("company");
			companyElement.addElement("ticker").setText("IBM");
			companyElement.addElement("name").setText("IBM Argentina");
			companyElement.addElement("share").setText("23%");
			companyElement.addElement("employees").setText("3000");
			companyElement.addElement("revenues").setText("129941");

			return new XmlSerializableImpl(document.asXML());
		}
		catch(Exception e)
		{
			throw new RuntimeException("Hay un problema en la accion de test", e);
		}
	}

	/**
	 * Generates a tree structure XML for displaying with some control
	 * @return Xml Tree structure
	 */
	public XmlSerializable getTreeStructure()
	{
		try
		{
    	Document document = DocumentHelper.parseText("<root></root>");
			Element rootElement = document.getRootElement();

			Element node1 = rootElement.addElement("node").addAttribute("name", "1");
			rootElement.addElement("node").addAttribute("name", "2");
			Element node3 = rootElement.addElement("node").addAttribute("name", "3");
			rootElement.addElement("node").addAttribute("name", "4");

			node1.addElement("node").addAttribute("name", "1.1");
			Element node12 = node1.addElement("node").addAttribute("name", "1.2");

			node3.addElement("node").addAttribute("name", "3.1");
			node3.addElement("node").addAttribute("name", "3.2");

			node12.addElement("node").addAttribute("name", "1.2.1");
			node12.addElement("node").addAttribute("name", "1.2.2");
			node12.addElement("node").addAttribute("name", "1.2.3");

			return new XmlSerializableImpl(document.asXML());
		}
		catch(Exception e)
		{
			throw new RuntimeException("Hay un problema en la accion de test", e);
		}
	}

	/**
	 * Executes a "critical operation" that fails and throws and exception
	 * @throws ServiceException Error to be thrown
	 */
	public void executeCriticalOperation() throws ServiceException
	{
		throw new ServiceException("Uno de los parametros que ha enviado son invalidos");
	}

	/**
	 * Receives a DTO
	 * @return Contents of the DTO, so they can be shown
	 */
	public XmlSerializable saveForm(Form form)
	{
		try
		{
    	Document document = DocumentHelper.parseText("<telefrontForm></telefrontForm>");
			Element rootElement = document.getRootElement();

			if (form.getTelefrontFormDataTwo() == null) form.setTelefrontFormDataTwo("");
			
			rootElement.addElement("telefrontFormDataOne").setText(form.getTelefrontFormDataOne());
			rootElement.addElement("telefrontFormDataTwo").setText(form.getTelefrontFormDataTwo());

			return new XmlSerializableImpl(document.asXML());
		}
		catch(Exception e)
		{
			throw new RuntimeException("Hay un problema en la accion de test", e);
		}
	}
}
