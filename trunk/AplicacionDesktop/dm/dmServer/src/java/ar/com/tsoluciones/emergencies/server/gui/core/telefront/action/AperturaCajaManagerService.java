package ar.com.tsoluciones.emergencies.server.gui.core.telefront.action;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import ar.com.tsoluciones.arcom.cor.ServiceException;
import ar.com.tsoluciones.arcom.security.GroupUser;
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
		
		User user = searchUserByFingerPrint(huellaDigital, userInterface);

		Session session = new Session(this.getHttpSession());
		session.setUser(user);

		return new XmlSerializableImpl(user.toXml().asXML());
	}

  	private User searchUserByFingerPrint(String huellaDigital, UserServiceInterface userInterface) throws ServiceException {
   		  User[] users = userInterface.getAll();
	    	boolean existUser = false;
			User user = null;
			
			for(int i = 0 ; i <  (int) users.length ; i++){
				user = users[i];
				if (existUser = user.authenticate(huellaDigital))
					break;
			}
			if(!existUser)
				throw new ServiceException("El usuario o la huella digital no son validos. Reintente nuevamente");
			return user;
	  }

	/**
	 * Deslogea al usuario, destruyendo las sesión
	 */
	public void logout() {
		new Session(this.getHttpSession()).destroy();
	}
	
	public XmlSerializable altaHuellaDigital(String username, String password, String huella, String huellaAlternativa) throws ServiceException{
		AperturaCajaServiceInterface aperturaCajaService = (AperturaCajaServiceInterface) new AperturaCajaServiceFactory().newInstance();
        User u = aperturaCajaService.altaHuellaDigital(username, password, huella, huellaAlternativa);
	    return new XmlSerializableImpl(u.toXml().asXML());
		
	}
	public XmlSerializable cerrarCaja(String username, String montoApertura, String fecha, String huellaDigital) throws ServiceException {
		UserServiceInterface userInterface = (UserServiceInterface) new UserServiceFactory().newInstance();
		User user = userInterface.getUserByUserName(username);
		
		if (! user.authenticate(huellaDigital))
			throw new ServiceException("El usuario que quiere cerrar la caja no es el usuario que la abrio. Reintente");
		
		Document document = DocumentHelper.createDocument();
	    Element rootElement = document.addElement("cerrarCaja");
			
	    rootElement.addElement("cajaCerrada").setText("OK");
	    return new XmlSerializableImpl(document.asXML());
			
	}
	  
	//--------------------------METODOS DEL SUPERVISOR
	
	public XmlSerializable cancelarVenta( String motivo, String huellaDigital) throws ServiceException {
		UserServiceInterface userInterface = (UserServiceInterface) new UserServiceFactory().newInstance();
		User user = searchUserByFingerPrint(huellaDigital, userInterface);
		
		//Validar que ese Usuario sea ademas un supervisor
		if(!user.getGroupUser().getName().equals(GroupUser.ROLE_SUPERVISOR))
			throw new ServiceException("El usuario que intenta realizar la operacion no tiene ROL \"Supervisor\"");

		//TODO persistir la operacion
		
		    return new XmlSerializableImpl(user.toXml().asXML());
		}
	
	public XmlSerializable validarSupervisor( String huellaDigital) throws ServiceException {
		UserServiceInterface userInterface = (UserServiceInterface) new UserServiceFactory().newInstance();
		User user = searchUserByFingerPrint(huellaDigital, userInterface);
		
		if(!user.getGroupUser().getName().equals(GroupUser.ROLE_SUPERVISOR))
			throw new ServiceException("El usuario que intenta realizar la operacion no tiene ROL \"Supervisor\"");
		
		    return new XmlSerializableImpl(user.toXml().asXML());
		}
	
	public XmlSerializable validarUsuario( String huellaDigital) throws ServiceException {
		
		UserServiceInterface userInterface = (UserServiceInterface) new UserServiceFactory().newInstance();
		User userEmpleado = searchUserByFingerPrint(huellaDigital, userInterface);
		    return new XmlSerializableImpl(userEmpleado.toXml().asXML());
		}
	
}
