package ar.com.tsoluciones.emergencies.server.gui.core.telefront.action;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;

import ar.com.tsoluciones.arcom.cor.ServiceException;
import ar.com.tsoluciones.arcom.security.User;
import ar.com.tsoluciones.arcom.security.services.factory.UserServiceFactory;
import ar.com.tsoluciones.arcom.security.services.proxyinterface.UserServiceInterface;
import ar.com.tsoluciones.emergencies.server.businesslogic.core.service.factory.AperturaCajaServiceFactory;
import ar.com.tsoluciones.emergencies.server.businesslogic.core.service.proxyinterface.AperturaCajaServiceInterface;
import ar.com.tsoluciones.emergencies.server.gui.core.session.Session;
import ar.com.tsoluciones.telefront.dispatcher.XmlSerializable;
import ar.com.tsoluciones.telefront.dispatcher.XmlSerializableImpl;
import ar.com.tsoluciones.telefront.servicefactory.TelefrontServiceFactory;


public class AperturaCajaManagerService extends TelefrontServiceFactory {
	/**
	 * Instancia un manager de Apertura de caja
	 *
	 * @return Manager de Apertura de caja
	 */
	@Override
	public Object newInstance() {
		return new AperturaCajaManagerService();
	}

  /**
   * Logea un usuario en el sistema
   *
   * @param username Nombre del usuario
   * @param password Su password
   * @param dutyId id de guardia
   * @param ip Número de IP del cliente que se quiere loguear (para determinar el workstation)
   * @throws ar.com.tsoluciones.arcom.cor.ServiceException
   *          Cuando hay un error de negocios, por ejemplo, si el usuario no se puede logear
   */
  public XmlSerializable abrirCaja(String username, String montoApertura, String fecha, String huellaDigital) throws ServiceException {
		UserServiceInterface userInterface = (UserServiceInterface) new UserServiceFactory().newInstance();
		User user = userInterface.getUserByUserName(username);
		
		if(user == null )
			throw new ServiceException("El usuario o la huella digital no son validos. Reintente nuevamente");

		
		//Si es primer logueo entonces no tiene huella asociada, devuelvo el user sin meter el usaurio en la session
		if(user.getHuelladigital() == null)
			return new XmlSerializableImpl(user.toXml().asXML());
		
		// Si se logeo...
		if (!user.authenticate(huellaDigital))
			throw new ServiceException("El usuario o la huella digital no son validos. Reintente nuevamente");


		Session session = new Session(this.getHttpSession());
		session.setUser(user);

		return new XmlSerializableImpl(user.toXml().asXML());
	}

	/**
	 * Deslogea al usuario, destruyendo las sesión
	 */
	public void logout() {
		new Session(this.getHttpSession()).destroy();
	}
	
	public XmlSerializable altaHuellaDigital(String username, String password, String huella) throws ServiceException{
		AperturaCajaServiceInterface aperturaCajaService = (AperturaCajaServiceInterface) new AperturaCajaServiceFactory().newInstance();
		if(!aperturaCajaService.altaHuellaDigital(username, password, huella))
			throw new ServiceException("La contraseña es invalida. Reintente nuevamente");

		Document doc = DocumentHelper.createDocument();

		return null;
	}


}
