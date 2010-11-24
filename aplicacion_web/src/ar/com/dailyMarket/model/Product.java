package ar.com.dailyMarket.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;

import org.hibernate.Hibernate;

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
	private Boolean active;
	private Image image;
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
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
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
}