package dailymarket.model;

import java.util.Date;

public class Empleado {

	private Long id;
	private String user;
	private String name;
	private String lastName;
	private String password;
	private String passwordOld;
	private String dni;
	private String dateCreated;
	private GroupEmpleado groupEmpleado;
	private String email;
	private boolean receiveNotifications;
	private String huelladigital;
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
	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	public GroupEmpleado getGroupEmpleado() {
		return groupEmpleado;
	}
	public void setGroupEmpleado(GroupEmpleado groupEmpleado) {
		this.groupEmpleado = groupEmpleado;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isReceiveNotifications() {
		return receiveNotifications;
	}
	public void setReceiveNotifications(boolean receiveNotifications) {
		this.receiveNotifications = receiveNotifications;
	}
	public String getHuelladigital() {
		return huelladigital;
	}
	public void setHuelladigital(String huelladigital) {
		this.huelladigital = huelladigital;
	}
	
	
	
	
}
