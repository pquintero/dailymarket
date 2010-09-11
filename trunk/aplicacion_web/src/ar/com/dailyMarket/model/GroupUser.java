package ar.com.dailyMarket.model;

public class GroupUser {
	public static final String ROLE_ADMIN = "Admin";
	public static final String ROLE_MANAGER = "Manager";
	public static final String ROLE_SUPERVISOR = "Supervisor";
	public static final String ROLE_CAJERO = "Cajero";
	
	private Long id;
	private String name;
	private String description;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}		
}
