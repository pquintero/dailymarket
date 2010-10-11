package ar.com.dailyMarket.model;

import java.util.Date;

public class SolicitudeStock {
	
	private Long id;
	private Product product;
	private Date dateSolicitude;
	private Boolean active;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Date getDateSolicitude() {
		return dateSolicitude;
	}
	public void setDateSolicitude(Date dateSolicitude) {
		this.dateSolicitude = dateSolicitude;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}		
}
