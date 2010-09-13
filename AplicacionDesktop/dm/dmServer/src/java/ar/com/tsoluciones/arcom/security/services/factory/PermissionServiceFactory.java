package ar.com.tsoluciones.arcom.security.services.factory;

import ar.com.tsoluciones.arcom.serviceproxy.ServiceFactory;
import ar.com.tsoluciones.arcom.security.services.implementation.PermissionService;
/*
 * <p>
 * Construye una implementación de manager de permisos
 * </p>
 *
 * @author despada
 * @version Aug 30, 2005, 2:55:43 PM
 * @see 
 */
public class PermissionServiceFactory extends ServiceFactory
{
	/**
	 * Construye la implementacion
	 * @return Implementacion de PermissionServiceInterface
	 */
	@Override
	public Object newInstance()
	{
		return this.newServiceInstance(new PermissionService());
	}
}
