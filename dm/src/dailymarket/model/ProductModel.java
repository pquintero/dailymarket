package dailymarket.model;

import java.util.HashMap;
import java.util.Map;
import java.util.prefs.AbstractPreferences;
import java.util.prefs.Preferences;

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
	private byte[] foto;
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
	
	public byte[] getFoto() {
		return foto;
	}
	public void setFoto(byte[] foto) {
		this.foto = foto;
	}
	public void toProductModel(Document doc){
		
		Element root = doc.getRootElement();
		
		if("".equals(root.selectSingleNode("id").getStringValue())){
			return;
		}
		
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
		String foto = root.selectSingleNode("foto").getStringValue();
		
		if(foto!=null && !"".equalsIgnoreCase(foto)){
			byte [] fotoArray = MyBase64.decode(foto);
			this.setFoto(fotoArray);
		}
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
	
	 public static class MyBase64 {
	     private static class MyPreferences extends AbstractPreferences {
	         private Map<String,String> map = new HashMap<String,String>();
	         MyPreferences() { super(null,""); }
	         protected void putSpi(String key,String value) { map.put(key,value); }
	         protected String getSpi(String key) { return map.get(key); }
	         protected void removeSpi(String key) { map.remove(key); }
	         protected void removeNodeSpi() { }
	         protected String[] keysSpi() { return null; }
	         protected String[] childrenNamesSpi() { return null; }
	         protected AbstractPreferences childSpi(String key) { return null; }
	         protected void syncSpi() {}
	         protected void flushSpi() {}
	     }
	     static String encode(byte[] ba) {
	         Preferences p = new MyPreferences();
	         p.putByteArray("",ba);
	         return p.get("",null);
	     }
	     static byte[] decode(String s) {
	         Preferences p = new MyPreferences();
	         p.put("",s);
	         return p.getByteArray("",null);
	     }
	     public static void main(String[] arg) {
	         byte[] ba = arg[0].getBytes();
	         String s = MyBase64.encode(ba);
	         System.out.println(s);
	         ba = MyBase64.decode(s);
	         s = new String(ba);
	         System.out.println(s);
	     }
	 }
	
	
}
