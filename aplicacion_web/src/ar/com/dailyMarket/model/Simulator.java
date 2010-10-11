package ar.com.dailyMarket.model;

import java.util.Date;

public class Simulator {
	
	private Date lastSimulator;
	private GroupProduct groupProduct;
	private Product product;
	private Boolean active;
	
	public Date getLastSimulator() {
		return lastSimulator;
	}
	public void setLastSimulator(Date lastSimulator) {
		this.lastSimulator = lastSimulator;
	}
	public GroupProduct getGroupProduct() {
		return groupProduct;
	}
	public void setGroupProduct(GroupProduct groupProduct) {
		this.groupProduct = groupProduct;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}	
}
