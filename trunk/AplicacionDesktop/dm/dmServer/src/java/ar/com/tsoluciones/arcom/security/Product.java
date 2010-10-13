package ar.com.tsoluciones.arcom.security;

import java.util.Date;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Product {
	
	public static final String PRODUCT_STATE_STOCK = "product.state.stock";
	public static final String PRODUCT_STATE_PENDING = "product.state.pending";
	public static final String PRODUCT_STATE_SEND = "product.state.send";	
	
	private Long id;
	private String name;
	private String description;
	private Integer actualStock;
	private Double price;
	private Integer sizeOfPurchase;
	private String code;
	private GroupProduct groupProduct;
	private String state;
	private Integer repositionStock;
	private Date dateWithoutStock; //fecha última en q se quedo sin stock
	
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getDateWithoutStock() {
		return dateWithoutStock;
	}
	public void setDateWithoutStock(Date dateWithoutStock) {
		this.dateWithoutStock = dateWithoutStock;
	}
	public Integer getRepositionStock() {
		return repositionStock;
	}
	public void setRepositionStock(Integer repositionStock) {
		this.repositionStock = repositionStock;
	}	
	
	/**
	 * <p>
	 * Retorna una representación mínima del producto.
	 * </p>
	 * @return Document
	 */
	public Document toXml() {

		Document doc = DocumentHelper.createDocument();
		doc.setRootElement(DocumentHelper.createElement("product"));
		
		Element root = doc.getRootElement();
		root.addElement("id").setText(id.toString());
		root.addElement("description").setText(description !=null ?String.valueOf(description):"");
		root.addElement("name").setText(name !=null ?String.valueOf(name):"");
		root.addElement("actualStock").setText(actualStock !=null ? String.valueOf(actualStock) :"");
		root.addElement("price").setText(price !=null ?String.valueOf(price):"");
		root.addElement("sizeOfPurchase").setText(sizeOfPurchase !=null ?String.valueOf(sizeOfPurchase):"");
		root.addElement("code").setText(code != null ? String.valueOf(code): "");
		root.addElement("state").setText(state != null ? String.valueOf(state):"");
		root.addElement("repositionStock").setText( repositionStock != null ? String.valueOf(repositionStock):"");
		root.addElement("dateWithoutStock").setText( dateWithoutStock != null ? String.valueOf(dateWithoutStock):"");
		
		Element groupUserEl = root.addElement("groupProduct");
		groupUserEl.addElement("id").setText(groupProduct.getId().toString());
		groupUserEl.addElement("name").setText(groupProduct.getName());
		groupUserEl.addElement("description").setText(groupProduct.getDescription());		

	    return doc;
	}
}