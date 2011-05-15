package ar.com.tsoluciones.arcom.security.services.factory;

import ar.com.tsoluciones.arcom.serviceproxy.ServiceFactory;
import ar.com.tsoluciones.arcom.security.services.implementation.RoleService;
/*
 * <p>
 * Construye clases manager de Role
 * </p>
 *
 * @author despada
 * @version Aug 24, 2005, 8:58:34 AM
 * @see 
 */

public class RoleServiceFactory extends ServiceFactory
{
	@Override
	public Object newInstance()
	{
		return super.newServiceInstance(new RoleService());
	}
}
