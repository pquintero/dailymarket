package ar.com.tsoluciones.arcom.security;

/**
 * <p>Esta clase representa a un usuario del sistema.</p>
 * @author Andres Herrera &lt;aherrera@artech-consulting.com&gt;
 * @version 2.0, 14/05/2004
 * @see ar.com.tsoluciones.arcom.security.User User
 * @see ar.com.tsoluciones.arcom.security.Permission Permission
 */

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlIDREF;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import ar.com.tsoluciones.arcom.hibernate.LogicDelete;
import ar.com.tsoluciones.arcom.logging.Log;
import ar.com.tsoluciones.arcom.security.handlers.SecurityHandler;
import ar.com.tsoluciones.arcom.security.handlers.SecurityHandlerFactory;
import ar.com.tsoluciones.arcom.security.services.implementation.RoleService;
import ar.com.tsoluciones.arcom.util.Address;
import ar.com.tsoluciones.arcom.util.Email;
import ar.com.tsoluciones.arcom.util.Phone;

@XmlAccessorType(XmlAccessType.NONE)
public class User implements Comparable<User>, Loginable, LogicDelete, Serializable {
    // fields in the User table of the db
    private Long id;

    @XmlAttribute
    private String username;
    @XmlAttribute
    private String firstName;
    @XmlAttribute
    private String lastName;
    @XmlAttribute
    private String title;
    private Date lastLogin;
    private boolean deleted;
    @XmlAttribute
    private boolean sysAdmin;
    private Set<Address> addresses;
    private Set<Phone> phones;
    private Set<Email> emails;
    @XmlElementWrapper(name = "roles")
    @XmlElement(name = "role")
    @XmlIDREF
    private Set<Role> roles;
    @XmlElementWrapper(name = "permissions")
    @XmlElement(name = "permission")
    @XmlIDREF
    private Set<Permission> permissions;
    
    private boolean robotUser = false;
    
    private String legajo;

	/**
     * <p>
     * Constructor por defecto de User
     * </p>
     */
    public User() {
        this.addresses = new HashSet<Address>();
        this.phones = new HashSet<Phone>();
        this.emails = new HashSet<Email>();
        this.roles = new HashSet<Role>();
        this.permissions = new HashSet<Permission>();
    }

    /**
     * Construye un usuario asignandole un id
     *
     * @param id
     *            Id de usuario
     */
    public User(Long id) {
        this.id = id;
    }

    /**
     * <p>
     * Devuelve el id del usuario
     * </p>
     */
    public Long getId() {
        return id;
    }

    /**
     * <p>
     * Define el id del Usuario, metodo de tipo private
     * </p>
     *
     * @param id
     *            que se desea setear para el usuario
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * <p>
     * Devuelve el username del usuario
     * </p>
     *
     * @return String - el username correspondiente al usuario
     */
    public String getUsername() {
        return username;
    }

    /**
     * <p>
     * Define el username del Usuario,
     * </p>
     *
     * @param username
     *            que se desea setear para el usuario
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * <p>
     * Devuelve el firstname del usuario
     * </p>
     *
     * @return String - el FirstName correspondiente al usuario
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * <p>
     * Define el firstname del Usuario,
     * </p>
     *
     * @param firstName
     *            que se desea setear para el usuario
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * <p>
     * Devuelve el lasttname del usuario
     * </p>
     *
     * @return String - el lastname correspondiente al usuario
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * <p>
     * Define el lasttname del Usuario,
     * </p>
     *
     * @param lastName
     *            que se desea setear para el usuario
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * <p>
     * Devuelve el title del usuario
     * </p>
     *
     * @return String - el lastname correspondiente al usuario
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * <p>
     * Define el title del Usuario,
     * </p>
     *
     * @param title
     *            que se desea setear para el usuario
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * <p>
     * informa si el Usuario es administrador del sistema,
     * </p>
     *
     * @return boolean true = es administrtador del sistema false = No es
     *         administrrador del sistema
     */
    public boolean isSysAdmin() {
        return this.sysAdmin;
    }

    /**
     * <p>
     * Define la propiedad de Usuario Administrador,
     * </p>
     *
     * @param sysAdmin
     *            true = es administrtador del sistema false = No es
     *            administrrador del sistema
     */
    public void setSysAdmin(boolean sysAdmin) {
        this.sysAdmin = sysAdmin;
    }

    /**
     * <p>
     * informa si el Usuario se encuentra blooqueado,
     * </p>
     *
     * @return boolean true = El usuario se encuantra bloqueado false = El
     *         usuario NO se encuantra bloqueado
     */
    public boolean isLocked() {
        return false;
    }

    /**
     * <p>
     * Devuelve el la ultima fecha de logeo del usuario
     * </p>
     *
     * @return Date - ultima fecha de logeo
     */
    public Date getLastLogin() {
        return lastLogin;
    }

    // only setable when authentication occurs, or when recovering user from DB

    /**
     * <p>
     * Define la fecha de logeo,
     * </p>
     *
     * @param lastLogin
     *            ultimo login
     */
    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    /**
     * <p>
     * informa si el Usuario se encuentra Borrado logicamente,
     * </p>
     *
     * @return boolean true = El usuario se encuantra borrado logicamente false =
     *         El usuario NO se encuantra borrado logicamente
     */
    public boolean isDeleted() {
        return this.deleted;
    }

    // only setable when recovering user from DB, otherwise should use delete()
    // or undelete()

    /**
     * <p>
     * Define la fecha de Expiracion,
     * </p>
     *
     * @param deleted
     *            la fecha de expiración
     */
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * <p>
     * Borra lógicamente al usuario
     * </p>
     */
    public void delete() {
        Log.security.debug("User marked for deletion " + this.username);
        this.deleted = true;
    }

    /**
     * <p>
     * Informa si el usuario tiene o no un deeterminado permiso
     * </p>
     *
     * @param permission
     *            el permiso a buscar
     * @return boolean true = Posee el permiso buscado false = NO Posee el
     *         permiso buscado
     */
    public boolean hasPermission(Permission permission) {

        if (getPermissions().contains(permission)) {
            return true;
        }

        Iterator<Role> i = getRoles().iterator();
        Role role = null;
        while (i.hasNext()) {
            role = i.next();

            if (role.getPermissions().contains(permission)) {
                return true;
            }
        }

        // The user and the roles do not contain the permission;
        return false;
    }

    /**
     * <p>
     * Agrega un permiso a un usuario
     * </p>
     *
     * @param permission =
     *            el permiso a agregar
     */
    public void addPermission(Permission permission) {
        // Si los Roles tienen el permiso que se esta tratando de asignar,
        // Hibernate causa un error.
        if (!hasPermission(permission)) {
            permissions.add(permission);
        }
    }

    /**
     * <p>
     * Quita un permiso a un usuario
     * </p>
     *
     * @param permission
     *            el permiso a remover
     */
    public void removePermission(Permission permission) {
        permissions.remove(permission);
    }

    /**
     * <p>
     * Quita todos los permisos individuales a un usuario (no los obtenidos por
     * Rol)
     * </p>
     */
    public void removeAllPermissions() {
        permissions.clear();
    }

    /**
     * Returns the set of permission either directly associated with this user
     * or associated with the roles associated with the user.
     */
    /**
     * <p>
     * Devuelve el conjunto ded permisos correspondiente a un usuario
     * </p>
     *
     * @return Set - Conjunto de permisos del usuario
     */
    public Set<Permission> getPermissions() {
        return permissions;
    }

    /**
     * <p>
     * Define el conjunto de permisos para el usuario
     * </p>
     *
     * @param permissions
     *            el conjunto de permisos
     */
    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    /**
     * <p>
     * Devuelve todos loos permisos, tanto propietarios como los heredados de
     * los roles que posee
     * </p>
     *
     * @return Set permission
     */
    public Set<Permission> obtainAllPermissions() {
        Set<Permission> perm = new HashSet<Permission>();

        for (Iterator<Permission> i = permissions.iterator(); i.hasNext();) {
            Permission next = i.next();
            if (!next.isDeleted()) {
                perm.add(next);
            }
        }
        for (Iterator<Role> i = roles.iterator(); i.hasNext();) {
            Role next = i.next();
            perm.addAll(next.getPermissions());
        }
        return perm;
    }

    /**
     * <p>
     * Informa si el usuario tiene o no un deeterminado rol
     * </p>
     *
     * @param role el rol a buscar
     * @return boolean true = Posee el rol buscado false = NO Posee el rol buscado
     */
    public Boolean hasRole(Role role) {
        Set<Role> rls = getRoles();
        if (rls.contains(role)) {
            return true;
        }

        return false;
    }
    
    /**
     * <p>
     * Informa si el usuario tiene o no un deeterminado rol a partir del nombre del rol
     * </p>
     *
     * @param role el rol a buscar
     * @return boolean true = Posee el rol buscado false = NO Posee el rol buscado
     */
    public Boolean hasRole(String role) {
    	RoleService rs = new RoleService();
    	Role r = rs.getByName(role);
    	return hasRole(r);
    }


    /**
     * <p>
     * Agrega un rol a un usuario
     * </p>
     *
     * @param role
     *            el rol a agregar
     */
    public void addRole(Role role) {
        Set<Permission> perm = role.getPermissions();
        roles.add(role);
        // Remove all permissions associated with this role, otherwise Hibernate
        // persistance will fail
        permissions.removeAll(perm);

    }

    /**
     * <p>
     * Quita un rol a un usuario
     * </p>
     *
     * @param role
     *            el rol a remover
     */
    public void removeRole(Role role) {
        roles.remove(role);
    }

    /**
     * <p>
     * Quita todos los roles a un usuario
     * </p>
     */
    public void removeAllRoles() {
        roles.clear();
    }

    /**
     * <p>
     * Define el conjunto de roles para el usuario
     * </p>
     *
     * @param roles
     *            el conjunto de roles
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    /**
     * <p>
     * Devuelve el conjunto de roles correspondiente a un usuario
     * </p>
     *
     * @return Set - Conjunto de roles del usuario
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * <p>
     * Devuelve el conjunto de Direcciones correspondiente a un usuario
     * </p>
     *
     * @return Set - Conjunto de direcciones del usuario
     */
    public Set<Address> getAddresses() {
        return addresses;
    }

    /**
     * <p>
     * Define el conjunto de direcciones para el usuario
     * </p>
     *
     * @param addresses
     *            el conjunto de direcciones
     */
    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    /**
     * <p>
     * Agrega una Direccion a un usuario
     * </p>
     *
     * @param address
     *            la Direccion a agregar
     */
    public void addAddress(Address address) {
        addresses.add(address);
    }

    /**
     * <p>
     * Quita una Direccion a un usuario
     * </p>
     *
     * @param address
     *            la Direccion a remover
     */
    public void removeAddress(Address address) {
        addresses.remove(address);
    }

    /**
     * <p>
     * Devuelve el conjunto de Telefonos correspondiente a un usuario
     * </p>
     *
     * @return Set - Conjunto de telefonos del usuario
     */
    public Set<Phone> getPhones() {
        return phones;
    }

    /**
     * <p>
     * Define el conjunto de telefonos para el usuario
     * </p>
     *
     * @param phones
     *            el conjunto de telefonos
     */
    public void setPhones(Set<Phone> phones) {
        this.phones = phones;
    }

    /**
     * <p>
     * Agrega un Telefono a un usuario
     * </p>
     *
     * @param phone
     *            el Telefono a agregar
     */
    public void addPhone(Phone phone) {
        phones.add(phone);
    }

    /**
     * <p>
     * Quita un Telefono a un usuario
     * </p>
     *
     * @param phone
     *            el Telefono a remover
     */
    public void removePhone(Phone phone) {
        phones.remove(phone);
    }

    /**
     * <p>
     * Devuelve el conjunto de e-mails correspondiente a un usuario
     * </p>
     *
     * @return Set - Conjunto de e-mails del usuario
     */
    public Set<Email> getEmails() {
        return emails;
    }

    /**
     * <p>
     * Define el conjunto de e-mails para el usuario
     * </p>
     *
     * @param emails
     *            el conjunto de e-mails
     */
    public void setEmails(Set<Email> emails) {
        this.emails = emails;
    }

    /**
     * <p>
     * Agrega un e-mail a un usuario
     * </p>
     *
     * @param email =
     *            el e-mail a agregar
     */
    public void addEmail(Email email) {
        emails.add(email);
    }

    /**
     * <p>
     * Quita un email a un usuario
     * </p>
     *
     * @param email =
     *            el email a remover
     */
    public void removeEmail(Email email) {
        emails.remove(email);
    }

    /**
	 * <p>
	 * Valida el usuario con su huella digital
	 * </p>
	 * @param huellaDigital - La huellaDigital a autentificar
	 * @return boolean true = La password es valida false= La password no es valida
	 */
    public boolean authenticate(String huellaDigital) {

    	boolean auth = true;
 
        return auth;
    }

    /**
     * Validates user password against the corresponding SecurityHandler
     * implementation
     */
    public boolean logout() {
        return false;
    }

    // Security methods...

    /**
     * <p>
     * Changes password, validating the oldPassword is correct
     * </p>
     *
     * @param oldPassword
     *            La password a autenticar
     * @param newPassword
     *            La password nueva
     * @return -1 if old password validation faild or there was an errro setting
     *         new password.
     */
    public int changePassword(String oldPassword, String newPassword) {
        Log.security.debug("Password change requested for: " + this.username);
        SecurityHandler security = (SecurityHandler)SecurityHandlerFactory.getInstance().newInstance();
        boolean auth = security.authenticate(this.username, oldPassword);
        if (auth) {
            return setPassword(security, newPassword);
        }

        Log.security.debug("Failed to authenticate user before changing password" + this.username);
        return -1;
    }

    /**
     * <p>
     * Changes password, with no validation
     * </p>
     *
     * @param password
     *            La password a autentificar
     * @return the number of rows affected or -1 if old password validation
     *         failed or there was an error setting new passord.
     */
    protected int setPassword(SecurityHandler security, String password) {
        Log.security.debug("Setting password for user: " + this.username);
        if (deleted) {
            Log.security.warn("Password for user: " + this.username
                            + " cannot be set because user is deleted. Undelete before changing password");
            return -1;
        }

        return security.setPassword(this.username, password);
    }

    /**
     * <p>
     * Resetea la contraseña
     * </p>
     *
     * @return String - la nueva contraseña
     */
    public String resetPassword() {
        Log.security.debug("Password reset requested for: " + this.getUsername());
        Random rand = new Random();
        StringBuilder password = new StringBuilder("");
        for (int i = 0; i < 4; i++)
            password.append(rand.nextInt(9));
        SecurityHandler security = (SecurityHandler)SecurityHandlerFactory.getInstance().newInstance();
        int set = setPassword(security, password.toString());
        if (set > 0) {
            return password.toString();
        }

        return null;
    }

    // methods overriden to use User in Hash collections

    /**
     * <p>
     * Compara el username del usuario con el de otro usuario recibido
     * </p>
     *
     * @return int 0 = equals <0 si el argumento es mayor lexicograficamente que
     *         el id >0 si el argumento es menor lexicograficamente que el id
     */
    public int compareTo(User obj) {
        return this.username.compareToIgnoreCase(obj.getUsername());
    }

    /**
     * <p>
     * Compara el username del usuario con el de otro usuario recibido sin tener
     * en cuenta mayusculas
     * </p>
     *
     * @return int 0 = equals <0 si el argumento es mayor lexicograficamente que
     *         el id >0 si el argumento es menor lexicograficamente que el id
     */
    @Override
	public boolean equals(Object obj) {
        if (!(obj instanceof User)) {
            return false;
        }
        return this.username.equalsIgnoreCase(((User) obj).getUsername());
    }

    /**
     * <p> HashCode del username del usuario </p>
     *
     * @return int
     */
    @Override
	public int hashCode() {
        if (username != null)
            return this.username.hashCode();
        return super.hashCode();
    }
	
	public boolean isRobotUser() {
		return robotUser;
	}

	public void setRobotUser(boolean robotUser) {
		this.robotUser = robotUser;
	}

	/**
	 * <p>
	 * Retorna una representación mínima del usuario.
	 * </p>
	 * @return Document
	 */
	public Document toXml() {

		Document doc = DocumentHelper.createDocument();
		doc.setRootElement(DocumentHelper.createElement("user"));

		Element root = doc.getRootElement();
		root.addElement("id").setText(id.toString());
		root.addElement("username").setText(String.valueOf(username));

	    return doc;
	}

	public String getLegajo() {
		return legajo;
	}

	public void setLegajo(String legajo) {
		this.legajo = legajo;
	}
	
}
