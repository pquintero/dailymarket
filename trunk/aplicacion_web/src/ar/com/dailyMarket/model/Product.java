package ar.com.dailyMarket.model;

public class Product {
	
	private Long id;
	private String name;
	private String description;
	private Integer actualStock;
	private Double price;
	private Integer sizeOfPurchase;
	private String code;
	private GroupProduct groupProduct;
	
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
	public Integer getActualStock() {
		return actualStock;
	}
	public void setActualStock(Integer actualStock) {
		this.actualStock = actualStock;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getSizeOfPurchase() {
		return sizeOfPurchase;
	}
	public void setSizeOfPurchase(Integer sizeOfPurchase) {
		this.sizeOfPurchase = sizeOfPurchase;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public GroupProduct getGroupProduct() {
		return groupProduct;
	}
	public void setGroupProduct(GroupProduct groupProduct) {
		this.groupProduct = groupProduct;
	}	
}
