package ar.com.tsoluciones.emergencies.server.gui.core.telefront.action;

import ar.com.tsoluciones.arcom.cor.ServiceException;
import ar.com.tsoluciones.arcom.security.User;
import ar.com.tsoluciones.arcom.security.handlers.SecurityHandler;
import ar.com.tsoluciones.arcom.security.handlers.SecurityHandlerFactory;
import ar.com.tsoluciones.arcom.security.services.factory.UserServiceFactory;
import ar.com.tsoluciones.arcom.security.services.proxyinterface.UserServiceInterface;
import ar.com.tsoluciones.arcom.serviceproxy.ServiceFactory;
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
		AperturaCajaServiceInterface aperturaCajaService = (AperturaCajaServiceInterface) new AperturaCajaServiceFactory().newInstance();
		boolean authenticated = aperturaCajaService.abrirCaja(username, montoApertura, fecha, huellaDigital);

		// Si se logeo...
		if (!authenticated)
			throw new ServiceException("El usuario o la contraseña no son válidos");

		// ...setear usuario en session
		UserServiceInterface userServiceInterface = (UserServiceInterface) new UserServiceFactory().newInstance();
		User userAuthenticated = userServiceInterface.getUserByUserName(username);

		Session session = new Session(this.getHttpSession());
		session.setUser(userAuthenticated);

		return new XmlSerializableImpl(userAuthenticated.toXml().asXML());
	}

	/**
	 * Deslogea al usuario, destruyendo las sesión
	 */
	public void logout() {
		new Session(this.getHttpSession()).destroy();
	}

	public int changePassword(String oldPassword, String newPassword) {
		Session session = new Session(this.getHttpSession());
		User user = session.getUser();

		ServiceFactory sf = SecurityHandlerFactory.getInstance();
		SecurityHandler security = (SecurityHandler) sf.newInstance();

		boolean auth = security.authenticate(user.getUsername(), oldPassword);
		if (auth) {
			return user.changePassword(oldPassword, newPassword);
		}
		return -1;
	}

}
