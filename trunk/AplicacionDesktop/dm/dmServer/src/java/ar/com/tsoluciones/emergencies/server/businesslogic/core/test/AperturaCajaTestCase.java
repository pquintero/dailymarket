package ar.com.tsoluciones.emergencies.server.businesslogic.core.test;

/**
 * LoginServiceTestCase.java
 * Copyright (c) Telefónica Soluciones
 * Todos los derechos reservados.
 */
import java.util.Date;

import junit.framework.TestCase;
import ar.com.tsoluciones.arcom.security.LoginHistory;
import ar.com.tsoluciones.arcom.security.User;
import ar.com.tsoluciones.arcom.security.services.factory.CajeroVentaFactory;
import ar.com.tsoluciones.arcom.security.services.factory.UserServiceFactory;
import ar.com.tsoluciones.arcom.security.services.proxyinterface.CajeroVentaServiceInterface;
import ar.com.tsoluciones.arcom.security.services.proxyinterface.UserServiceInterface;
import ar.com.tsoluciones.arcom.system.SystemManager;
import ar.com.tsoluciones.emergencies.server.businesslogic.core.service.AperturaCajaService;

/**
 * <p/>
 * Prueba de la clase LoginService
 * </p>
 *
 * @author  atoranzo
 * @version 31/05/2005, 16:35:54
 * @see
 */
public class AperturaCajaTestCase 
{
	public static void main(String[] args) {
		
		SystemManager.init();
		
		login();
		
	    logout();
		
	}

	private static void logout() {
		CajeroVentaServiceInterface cajeroVentaServiceInterface = (CajeroVentaServiceInterface) new CajeroVentaFactory().newInstance();
		UserServiceInterface userInterface = (UserServiceInterface) new UserServiceFactory().newInstance();
		User user = userInterface.get(new Long(3));
	    LoginHistory loginHistory = cajeroVentaServiceInterface.getLoginHistory(user);
		loginHistory.setFechaCierre(new Date());
		loginHistory.setMontoCierre(new Double(10));
		
		cajeroVentaServiceInterface.updateLoginHistory(loginHistory);
	}

	private static void login() {
		UserServiceInterface userInterface = (UserServiceInterface) new UserServiceFactory().newInstance();
		User user = userInterface.get(new Long(3));
		
		CajeroVentaServiceInterface cajeroVentaServiceInterface = (CajeroVentaServiceInterface) new CajeroVentaFactory().newInstance();
		LoginHistory loginHistory = new LoginHistory();
		loginHistory.setCajero(user);
		loginHistory.setFechaApertura(new Date());
		loginHistory.setMontoApertura(new Double(22));
		loginHistory.setIdCaja(new Long(1));
		
		cajeroVentaServiceInterface.saveLoginHistory(loginHistory);
	}

}
