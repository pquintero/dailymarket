package ar.com.tsoluciones.arcom.security.services.factory;

import ar.com.tsoluciones.arcom.security.services.CajeroVentaService;
import ar.com.tsoluciones.arcom.serviceproxy.ServiceFactory;

public class CajeroVentaFactory extends ServiceFactory
{

	  /**
	   * Construye el objeto
	   * @return Un objeto CajeroVentaServiceInterface
	   */
	  @Override
	public Object newInstance()
	  {
	    return this.newServiceInstance(new CajeroVentaService());
	  }
}
