package ar.com.dailyMarket.model;

public class Configuration {
	
	private Long id;
	private Integer timer;
	private String emailDeposito;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getTimer() {
		return timer;
	}
	public void setTimer(Integer timer) {
		this.timer = timer;
	}
	public String getEmailDeposito() {
		return emailDeposito;
	}
	public void setEmailDeposito(String emailDeposito) {
		this.emailDeposito = emailDeposito;
	}	
}
