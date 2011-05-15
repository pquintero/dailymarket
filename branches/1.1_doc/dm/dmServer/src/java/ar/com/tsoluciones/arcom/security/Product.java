package ar.com.tsoluciones.arcom.security;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hibernate.Hibernate;

import ar.com.tsoluciones.arcom.security.User.MyBase64;

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
	private byte[] foto;
	
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
	
	public byte[] getFoto() {
		return foto;
	}
	
	 /** Don't invoke this.  Used by Hibernate only. */
	 public Blob getFotoBlob() {
		 if(foto != null)
	  return Hibernate.createBlob(this.foto);
		 else
			 return null;
	 }
	
	public void setFoto(byte[] foto) {
		this.foto = foto;
	}
	
	public void setFotoBlob(Blob fotoBlob) {
		if(fotoBlob!= null)
		  this.foto = this.toByteArray(fotoBlob);
		 }
	
	
	private byte[] toByteArray(Blob fromBlob) {
		  ByteArrayOutputStream baos = new ByteArrayOutputStream();
		  try {
		   return toByteArrayImpl(fromBlob, baos);
		  } catch (SQLException e) {
		   throw new RuntimeException(e);
		  } catch (IOException e) {
		   throw new RuntimeException(e);
		  } finally {
		   if (baos != null) {
		    try {
		     baos.close();
		    } catch (IOException ex) {
		    }
		   }
		  }
		 }

	 private byte[] toByteArrayImpl(Blob fromBlob, ByteArrayOutputStream baos)
	  throws SQLException, IOException {
	  byte[] buf = new byte[4000];
	  InputStream is = fromBlob.getBinaryStream();
	  try {
	   for (;;) {
	    int dataSize = is.read(buf);

	    if (dataSize == -1)
	     break;
	    baos.write(buf, 0, dataSize);
	   }
	  } finally {
	   if (is != null) {
	    try {
	     is.close();
	    } catch (IOException ex) {
	    }
	   }
	  }
	  return baos.toByteArray();
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
		root.addElement("id").setText(id!=null ?id.toString():"");
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
		groupUserEl.addElement("id").setText(groupProduct!=null ?groupProduct.getId().toString():"");
		groupUserEl.addElement("name").setText(groupProduct!=null ?groupProduct.getName():"");
		groupUserEl.addElement("description").setText(groupProduct!=null ?groupProduct.getDescription():"");
		
		root.addElement("foto").setText( foto != null ? MyBase64.encode(foto):"");

	    return doc;
	}
}