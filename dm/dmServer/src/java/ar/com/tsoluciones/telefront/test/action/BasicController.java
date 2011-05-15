package ar.com.tsoluciones.telefront.test.action;

import ar.com.tsoluciones.telefront.dispatcher.XmlSerializable;
import ar.com.tsoluciones.telefront.dispatcher.XmlSerializableImpl;
import ar.com.tsoluciones.telefront.servicefactory.TelefrontServiceFactory;

import java.util.Date;

/*
 * <p>
 * Controlador basico para pruebas
 * </p>
 *
 * @author despada
 * @version Aug 25, 2005, 12:06:25 PM
 * @see 
 */
public class BasicController extends TelefrontServiceFactory
{
	/**
	 * Genera una instancia del Controller
	 * @return instancia del Controller
	 */
	@Override
	public Object newInstance()
	{
		return new BasicController();
	}

	/**
	 * Devuelve un string "Hello World"
	 * @return "Hello World"
	 */
	public XmlSerializable executeHelloWorld(String helloWorld)
	{
		return new XmlSerializableImpl("<string>" + new Date().toString() + " - " + helloWorld + "</string>");
	}
}