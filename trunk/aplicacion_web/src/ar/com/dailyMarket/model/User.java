package ar.com.dailyMarket.model;

import java.util.Date;

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
}
