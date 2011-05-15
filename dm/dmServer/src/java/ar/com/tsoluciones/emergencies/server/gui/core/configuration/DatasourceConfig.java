package ar.com.tsoluciones.emergencies.server.gui.core.configuration;

public class DatasourceConfig {
	
	private String name;
	private String description;
	private String url;
	private String username;
	private String password;
	private Long daysLimit;
	private int poolMinSize;
	private int poolMaxSize;
	private int idleTestPeriod;
	
	private boolean defaultDatasource = false;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isDefaultDatasource() {
		return defaultDatasource;
	}
	public void setDefaultDatasource(boolean defaultDatasource) {
		this.defaultDatasource = defaultDatasource;
	}
	public Long getDaysLimit() {
		return daysLimit;
	}
	public void setDaysLimit(Long daysLimit) {
		this.daysLimit = daysLimit;
	}
	public Integer getPoolMinSize() {
		return poolMinSize;
	}
	public void setPoolMinSize(Integer poolMinSize) {
		this.poolMinSize = poolMinSize;
	}
	public Integer getPoolMaxSize() {
		return poolMaxSize;
	}
	public void setPoolMaxSize(Integer poolMaxSize) {
		this.poolMaxSize = poolMaxSize;
	}
	public Integer getIdleTestPeriod() {
		return idleTestPeriod;
	}
	public void setIdleTestPeriod(Integer idleTestPeriod) {
		this.idleTestPeriod = idleTestPeriod;
	}
}
