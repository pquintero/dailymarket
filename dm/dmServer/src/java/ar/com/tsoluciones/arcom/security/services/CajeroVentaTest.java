package ar.com.tsoluciones.arcom.security.services;

import ar.com.tsoluciones.arcom.security.SesionVenta;
import ar.com.tsoluciones.arcom.security.services.factory.CajeroVentaFactory;
import ar.com.tsoluciones.arcom.security.services.proxyinterface.CajeroVentaServiceInterface;
import ar.com.tsoluciones.arcom.system.SystemManager;

public class CajeroVentaTest {
	
	public static void main(String[] args) {
		
		SystemManager.init();
		
		CajeroVentaServiceInterface cajeroService = (CajeroVentaServiceInterface) new CajeroVentaFactory().newInstance();
		
		SesionVenta sesionVenta = cajeroService.obtenerSesionVenta(new Long(11));
		
		
		
		
		
	}
	

}
