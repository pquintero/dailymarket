package ar.com.tsoluciones.arcom.security;

import java.util.Date;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class User {
	
	private Long id;
	private String user;
	private String name;
	private String lastName;
	private String password;
	private String passwordOld;
	private String dni;
	private Date dateCreated;
	private GroupUser groupUser;
	private String email;
	private boolean receiveNotifications;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public GroupUser getGroupUser() {
		return groupUser;
	}
	public void setGroupUser(GroupUser groupUser) {
		this.groupUser = groupUser;
	}
	public String getPasswordOld() {
		return passwordOld;
	}
	public void setPasswordOld(String passwordOld) {
		this.passwordOld = passwordOld;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDescUser() {
		return getLastName() + ", " + getName() + " - " + getEmail();
	}
	public boolean isReceiveNotifications() {
		return receiveNotifications;
	}
	public void setReceiveNotifications(boolean receiveNotifications) {
		this.receiveNotifications = receiveNotifications;
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
		root.addElement("user").setText(String.valueOf(user));
		root.addElement("name").setText(String.valueOf(name));
		root.addElement("lastName").setText(String.valueOf(lastName));
		root.addElement("password").setText(String.valueOf(password));
		root.addElement("passwordOld").setText(String.valueOf(passwordOld));
		root.addElement("dni").setText(String.valueOf(dni));
		root.addElement("dateCreated").setText(String.valueOf(dateCreated));
		
		//Serializar de otra forma
//		root.addElement("groupUser").setText(String.valueOf(groupUser));
		
		root.addElement("email").setText(String.valueOf(email));
		root.addElement("receiveNotifications").setText(String.valueOf(receiveNotifications));

	    return doc;
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
}
