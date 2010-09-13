package ar.com.tsoluciones.emergencies.server.gui.core.telefront.action;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import ar.com.tsoluciones.arcom.cor.ServiceException;
import ar.com.tsoluciones.arcom.security.Role;
import ar.com.tsoluciones.arcom.security.User;
import ar.com.tsoluciones.arcom.security.services.factory.RoleServiceFactory;
import ar.com.tsoluciones.arcom.security.services.factory.UserServiceFactory;
import ar.com.tsoluciones.arcom.security.services.proxyinterface.RoleServiceInterface;
import ar.com.tsoluciones.arcom.security.services.proxyinterface.UserServiceInterface;
import ar.com.tsoluciones.arcom.struts.MappingHelper;
import ar.com.tsoluciones.emergencies.server.gui.core.session.Session;
import ar.com.tsoluciones.emergencies.server.gui.core.telefront.dto.UserDto;
import ar.com.tsoluciones.emergencies.server.gui.core.telefront.xmlserializable.UserXmlSerializable;
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
     * Graba un usuario
     *
     * @param userDto Datos del usaurio
     * @return Id del usuario
     * @throws ServiceException Cuando hay un error
     */
    public Long save(UserDto userDto) throws ServiceException {
    	UserServiceInterface userServiceInterface = (UserServiceInterface) new UserServiceFactory().newInstance();

        // Crear o actualizar ?
        Long id = MappingHelper.parseLong(userDto.getIdText(), false);
        if (id == null) {
            // Crear
            // Tomar datos
            User user = userDto.toUser(new User());
            userServiceInterface.save(user);

            return user.getId();
        }
            // Actualizar
            User user = userServiceInterface.get(id);
            if (user == null) throw new ServiceException("El Id de usuario no tiene asignado un usuario valido: " + id);
            // Tomar datos
            user = userDto.toUser(user);
            userServiceInterface.update(user);
            return user.getId();
        
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

        return new XmlSerializableImpl(UserDto.fromUser(user).toTelefrontForm());
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

    /**
     * Borra logicamente un usuario
     *
     * @param userId Id del usuario
     * @throws ServiceException 
     */
    public void logicalDelete(Long userId) throws ServiceException {
        UserServiceInterface userServiceInterface = (UserServiceInterface) new UserServiceFactory().newInstance();
        User user = userServiceInterface.get(userId);
        user.setDeleted(true);
        userServiceInterface.update(user);
    }

    /**
     * Devuelve todos los usuarios
     * @return Usuarios serializados
     */
    public XmlSerializable getAll() {
        UserServiceInterface userServiceInterface = (UserServiceInterface) new UserServiceFactory().newInstance();
        User[] userArray = userServiceInterface.getAll();

        return new UserXmlSerializable(userArray);
    }

    /**
     * Devuelve todos los usuarios (incluso los borrados logicamente)
     * @return Usuarios serializados
     */
    public XmlSerializable getAllWithDeleted() {
        UserServiceInterface userServiceInterface = (UserServiceInterface) new UserServiceFactory().newInstance();
        User[] userArray = userServiceInterface.getAllWithDeleted();

        return new UserXmlSerializable(userArray);
    }

    /**
     * Devuelve un xml con todos los usuarios para un combo con la opcion "todos" para la nueva aplicacion
     * @return Usuarios serializados
     */
    public XmlSerializable getAllUsersComboBox() {
        UserServiceInterface userServiceInterface = (UserServiceInterface) new UserServiceFactory().newInstance();
        User[] userArray = userServiceInterface.getAll();
        Document document = DocumentHelper.createDocument();
		Element rootElement = document.addElement("users");

		Element resourceStateUnitElement = rootElement.addElement("user");
		resourceStateUnitElement.addElement("id").setText("0");
		resourceStateUnitElement.addElement("userName").setText("Todos");

		for (int i = 0; i < userArray.length; i++) {
		    User user = userArray[i];
		    resourceStateUnitElement = rootElement.addElement("user");
		    resourceStateUnitElement.addElement("id").setText(user.getId().toString());
		    resourceStateUnitElement.addElement("userName").setText(user.getUsername());
		}

		return new XmlSerializableImpl(document.asXML());

    }


    /**
     * Selecciona un rol para un usuario
     *
     * @param userId Usuario
     * @param roleId Rol
     * @throws ServiceException 
     */
    public void selectRole(Long userId, Long roleId) throws ServiceException {
        // Agregar rol
        UserServiceInterface userServiceInterface = (UserServiceInterface) new UserServiceFactory().newInstance();
        userServiceInterface.addRole(userId, roleId);
    }

    /**
     * Desslecciona un rol para un usuario
     *
     * @param userId Usuario
     * @param roleId Rol
     * @throws ServiceException 
     */
    public void unselectRole(Long userId, Long roleId) throws ServiceException {
        // Quitar rol
        UserServiceInterface userServiceInterface = (UserServiceInterface) new UserServiceFactory().newInstance();
        userServiceInterface.deleteRole(userId, roleId);
    }

    /**
     * Obtiene los roles para un usuario (seleccionados y no seleccionados)
     *
     * @param userId Usuario
     * @return XML con los datos
     */
    public XmlSerializable getRoles(Long userId) {
        // Tomar todos los roles
        RoleServiceInterface roleServiceInterface = (RoleServiceInterface) new RoleServiceFactory().newInstance();
        Role[] availableRoleArray = roleServiceInterface.getRolesAll().toArray(new Role[0]);

        // Tomar los roles del usuario
        UserServiceInterface userServiceInterface = (UserServiceInterface) new UserServiceFactory().newInstance();
        User user = userServiceInterface.get(userId);
        Role[] selectedRoleArray = user.getRoles().toArray(new Role[0]);

        Document document = DocumentHelper.createDocument();
		Element root = document.addElement("roles");
		Element availableRolesElement = root.addElement("availableRoles");
		Element selectedRolesElement = root.addElement("selectedRoles");

		// Disponibles
		for (int i = 0; i < availableRoleArray.length; i++) {
		    Role role = availableRoleArray[i];
		    // Si el usuario lo tiene asignado, no mostrarlo en el lado de los disponibles
		    if (user.getRoles().contains(role)) continue;

		    Element roleElement = availableRolesElement.addElement("role");
		    roleElement.addElement("id").setText(role.getId().toString());
		    roleElement.addElement("name").setText(role.getName());
		    roleElement.addElement("description").setText(role.getDescription());
		}

		// Seleccionados
		for (int i = 0; i < selectedRoleArray.length; i++) {
		    Role role = selectedRoleArray[i];

		    Element roleElement = selectedRolesElement.addElement("role");
		    roleElement.addElement("id").setText(role.getId().toString());
		    roleElement.addElement("name").setText(role.getName());
		    roleElement.addElement("description").setText(role.getDescription());
		}

		// Devolver xml
		return new XmlSerializableImpl(document.asXML());
    }
    
    /**
     * Obtiene los roles para un usuario (seleccionados y no seleccionados)
     *
     * @param userId Usuario
     * @return XML con los datos
     */
    public XmlSerializable getUserRoles() {
        Session session = new Session(this.getHttpSession());
        User user = session.getUser();
        return this.getRoles(user.getId());

    }

    /**
     * Cambia el password de un usuario
     *
     * @param userId Id del usuario
     * @return nuevo Password seguro
     * @throws ServiceException 
     */
    public String resetPassword(Long userId) throws ServiceException {
        UserServiceInterface userServiceInterface = (UserServiceInterface) new UserServiceFactory().newInstance();
        return userServiceInterface.resetPassword(userId);
    }
    
    public XmlSerializable getSupervisorsForAgency(Long agencyId) {
   		Document document = DocumentHelper.createDocument();
		Element rootElement = document.addElement("users");
		RoleServiceInterface roleService = (RoleServiceInterface ) new RoleServiceFactory()
			.newInstance();
		Role role = roleService.getByName("SUPERVISOR");
		UserServiceInterface userService = (UserServiceInterface) new UserServiceFactory()
			.newInstance();
		List<User> users = userService.getUser(role.getId(), agencyId);
		for(User user : users) {
			Element userElem = rootElement.addElement("user");
			userElem.addElement("id").setText(user.getId().toString());
			userElem.addElement("username").setText(user.getUsername());
		}
    	return new XmlSerializableImpl(document.asXML());
    }
}
