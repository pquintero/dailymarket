package ar.com.tsoluciones.emergencies.server.businesslogic.core.service.factory;

import ar.com.tsoluciones.arcom.serviceproxy.ServiceFactory;
import ar.com.tsoluciones.emergencies.server.businesslogic.core.service.AperturaCajaService;

/**
 * Created by IntelliJ IDEA.
 * User: Agiannotti
 * Date: 29/11/2004
 * Time: 12:08:00
 * To change this template use File | Settings | File Templates.
 */
public class AperturaCajaServiceFactory extends ServiceFactory{


    	@Override
		public Object newInstance()
	{
		return this.newServiceInstance(new AperturaCajaService());
	}
}
