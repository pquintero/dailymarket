package ar.com.tsoluciones.emergencies.server.businesslogic.core.service.proxyinterface;

import ar.com.tsoluciones.arcom.cor.ServiceException;
import ar.com.tsoluciones.arcom.security.User;

public interface AperturaCajaServiceInterface
{

    
	public User altaHuellaDigital(String username, String password, String huella , String huellaAlternativa) throws ServiceException;


}
