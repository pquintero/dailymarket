package ar.com.dailyMarket.util;

public class CombSelect {
	protected String groupProduct;
	protected Long groupId;
	protected String product;
	protected Long productId;
	
	public CombSelect() {
		super();
	}
	
	public CombSelect(String group, Long gId, String prod, Long prodId) {
		super();
		this.groupProduct = group;
		this.groupId = gId;
		this.product = prod;
		this.productId = prodId;
	}
	
	public String getGroupProduct() {
		return groupProduct;
	}
	public void setGroupProduct(String groupProduct) {
		this.groupProduct = groupProduct;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
}
