package ar.com.tsoluciones.emergencies.server.businesslogic.core.service;


import ar.com.tsoluciones.arcom.logging.Log;
import ar.com.tsoluciones.arcom.security.User;
import ar.com.tsoluciones.arcom.security.services.implementation.UserService;
import ar.com.tsoluciones.arcom.security.services.proxyinterface.UserServiceInterface;
import ar.com.tsoluciones.emergencies.server.businesslogic.core.service.proxyinterface.AperturaCajaServiceInterface;


public class AperturaCajaService implements AperturaCajaServiceInterface {
	/**
	 * Logea un usuario en el sistema
	 *
	 */
	public boolean abrirCaja(String username, String montoApertura, String fecha, String huellaDigital) {
		UserServiceInterface userServiceInterface = new UserService();
		User userToAuthenticate = userServiceInterface.getUserByUserName(username);

		if (userToAuthenticate == null) {
			Log.getLogger(this.getClass()).debug("Usuario no encontrado");
			return false;
		}

		Log.getLogger(this.getClass()).debug("Autenticando: " + username);
		return userToAuthenticate.authenticate(huellaDigital);
	}

	public boolean isFirstLogin(String username, String password) {
		
		UserServiceInterface userServiceInterface = new UserService();
		User user = userServiceInterface.getUserByUserName(username);
		if(user.getHuelladigital() == null || user.getHuelladigital().length == 0)
			return true;
		else 
			return false;
	}
	
	public boolean altaHuellaDigital(String username, String password, String huella ){
		
		if(isFirstLogin(username, password))
			
			
		;
		return true;
	}
}
