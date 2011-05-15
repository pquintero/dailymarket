package ar.com.tsoluciones.emergencies.server.gui.core.telefront.action;

import ar.com.tsoluciones.arcom.cor.ServiceException;
import ar.com.tsoluciones.arcom.security.User;
import ar.com.tsoluciones.arcom.security.services.factory.UserServiceFactory;
import ar.com.tsoluciones.arcom.security.services.proxyinterface.UserServiceInterface;
import ar.com.tsoluciones.telefront.dispatcher.XmlSerializable;
import ar.com.tsoluciones.telefront.dispatcher.XmlSerializableImpl;
import ar.com.tsoluciones.telefront.servicefactory.TelefrontServiceFactory;

/**
 * Copyright (c) Telefónica Soluciones
 * Todos los derechos reservados.
 */

/**
 * <p>PageController java para las paginas de Admin usuarios</p>
 *
 * @author despada
 * @version 1.0, Sep 15, 2005, 10:46:58 AM
 */
public class UserManagerService extends TelefrontServiceFactory {
    @Override
	public Object newInstance() {
        return new UserManagerService();
    }

    /**
     * Obtiene un usuario
     *
     * @param userId Id del usuario
     * @return Datos serializados
     */
    public XmlSerializable get(Long userId) throws ServiceException {
        UserServiceInterface userServiceInterface = (UserServiceInterface) new UserServiceFactory().newInstance();
        User user = userServiceInterface.get(userId);

        return new XmlSerializableImpl(user.toXml().getText());
    }

    /**
     * Borra fisicamente un usuario
     *
     * @param userId Id del usuario
     */
    public void delete(Long userId) {
        UserServiceInterface userServiceInterface = (UserServiceInterface) new UserServiceFactory().newInstance();
        User user = userServiceInterface.get(userId);
        userServiceInterface.delete(user);
    }

    
}
