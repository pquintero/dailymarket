package dailymarket.model;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

public class ProductModel {

	
	private Long id;
	private String name;
	private String description;
	private Integer actualStock;
	private Double price;
	private Integer sizeOfPurchase;
	private String code;
	private GroupProductModel groupProduct;
	private String state;
	private Integer repositionStock;
	private String dateWithoutStock; //fecha última en q se quedo sin stock
	private Integer cantidad;
	
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
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
	public GroupProductModel getGroupProduct() {
		return groupProduct;
	}
	public void setGroupProduct(GroupProductModel groupProduct) {
		this.groupProduct = groupProduct;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Integer getRepositionStock() {
		return repositionStock;
	}
	public void setRepositionStock(Integer repositionStock) {
		this.repositionStock = repositionStock;
	}
	public String getDateWithoutStock() {
		return dateWithoutStock;
	}
	public void setDateWithoutStock(String dateWithoutStock) {
		this.dateWithoutStock = dateWithoutStock;
	}
	
	public void toProductModel(Document doc){
		
		Element root = doc.getRootElement();
		
		Long id = Long.valueOf(root.selectSingleNode("id").getStringValue());
		String description = root.selectSingleNode("description").getStringValue();
		String name = root.selectSingleNode("name").getStringValue();
		String actualStock = root.selectSingleNode("actualStock").getStringValue();
		String price = root.selectSingleNode("price").getStringValue();
		String sizeOfPurchase = root.selectSingleNode("sizeOfPurchase").getStringValue();
		String code = root.selectSingleNode("code").getStringValue();
		String state = root.selectSingleNode("state").getStringValue();
		String repositionStock = root.selectSingleNode("repositionStock").getStringValue();
		String dateWithoutStock = root.selectSingleNode("dateWithoutStock").getStringValue();
		this.setId(id);
		this.setDescription(description);
		this.setName(name);
		this.setActualStock(Integer.valueOf(actualStock));
		this.setPrice(Double.valueOf(price));
		this.setSizeOfPurchase(Integer.valueOf(sizeOfPurchase));
		this.setCode(code);
		this.setState(state);
		this.setRepositionStock(Integer.valueOf(repositionStock));
		this.setDateWithoutStock(dateWithoutStock);
		
		
		GroupProductModel groupProductModel = new GroupProductModel();
		Node groupProduct = root.selectSingleNode("groupProduct");
		Long idProduct = Long.valueOf(groupProduct.selectSingleNode("id").getStringValue());
		String nameProduct = groupProduct.selectSingleNode("name").getStringValue();
		String descriptionProduct = groupProduct.selectSingleNode("description").getStringValue();
		groupProductModel.setId(idProduct);
		groupProductModel.setName(nameProduct);
		groupProductModel.setDescription(descriptionProduct);
		this.setGroupProduct(groupProductModel);
		
	}
	
	
}
