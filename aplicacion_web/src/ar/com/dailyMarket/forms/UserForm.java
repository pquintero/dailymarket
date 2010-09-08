package ar.com.dailyMarket.forms;


public class UserForm extends BaseForm{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private String lastName;
	private String passwordOld;
	private String password;
	private String password2;
	private String groupName;
	private String createdBy;
	private String dateCreatedStr;
	private Boolean firstTime;	
		
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getPasswordOld() {
		return passwordOld;
	}
	public void setPasswordOld(String passwordOld) {
		this.passwordOld = passwordOld;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword2() {
		return password2;
	}
	public void setPassword2(String password2) {
		this.password2 = password2;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getDateCreatedStr() {
		return dateCreatedStr;
	}
	public void setDateCreatedStr(String dateCreatedStr) {
		this.dateCreatedStr = dateCreatedStr;
	}
	public Boolean getFirstTime() {
		return firstTime;
	}
	public void setFirstTime(Boolean firstTime) {
		this.firstTime = firstTime;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	} 
}
